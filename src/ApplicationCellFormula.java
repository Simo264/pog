import java.awt.*;

/**
 * E' una specializzazione del tipo ApplicationCell.
 * Rappresenta una formula (una stringa che inizia con il carattere '=')
 * all'interno di una cella.
 *
 * Una formula è rappresentata dal seguente formato:
 * =<coordinata/valore>[+-]<coordinata/valore>
 * esempio: =A0, =12+A0, =12+12, =A0+A1...
 *
 * Ammette 3 operazioni: assegnazione, somma e sottrazione.
 * 1. Assegnazione: ammette un solo operando di tipo coordinata o un valore numerico/testuale
 *  Esempi: =A0, =hello, =12, =B12
 *
 * 2. Somma: ammette due operandi che possono essere di tipo coordinata o valori numerici/testuali
 * Esempi: =A0+B0, =12+A0, =12+12, =hello+A0 =hello+world ...
 *
 * 3. Sottrazione: simile alla somma con la differenza che non ammette valori testuali.
 */
public class ApplicationCellFormula extends ApplicationCell
{
  private String formula = null;

  private enum EOperations { SUM, SUB, ASSIGNMENT }


  /**
   * Viene passato un oggetto di tipo Object
   * @param o
   */
  ApplicationCellFormula(Object o)
  {
    super(o);
    formula = o.toString().substring(1);
  }

  /**
   * Viene passato un oggetto ApplicationCell per copiare il contenuto
   * salvato nella cella
   * @param cell
   */
  ApplicationCellFormula(ApplicationCell cell)
  {
    super(cell.getData());
    formula = cell.getData().toString().substring(1);
  }

  @Override
  public boolean containsFormula() { return true; }


  /**
   * Controlla che la formula inserita sia valida o meno.
   * @return true se la formula è una formula valida, false altrimenti
   */
  @Override
  public boolean isValid()
  {
    if(formula.isEmpty())
      return false;

    String[] strSplit = formula.split("-|\\+");
    if((operation() == EOperations.SUB || operation() == EOperations.SUM)
        && strSplit.length == 1)
      return false;

    return true;
  }

  /**
   * @return la formula in formato testuale
   */
  @Override
  public Object getData()
  {
    return formula;
  }

  /**
   * Viene risolta la formula e viene ritornato il risultato
   * @param tableModel
   * @return il risulato nel tipo Object
   */
  public Object resolve(ApplicationTableModel tableModel)
  {
    var op = operation();
    switch (op)
    {
      case SUB ->
      {
        return resolveSub(tableModel);
      }
      case SUM ->
      {
        return resolveSum(tableModel);
      }
      case ASSIGNMENT ->
      {
        return resolveAssignment(tableModel);
      }
    }
    return null;
  }



  private Object resolveAssignment(ApplicationTableModel tableModel)
  {
    if(!ApplicationCoordinate.isValid(formula))
      return null;

    ApplicationCoordinate coordinate = new ApplicationCoordinate(formula);
    Point p = coordinate.reverse();
    Object o = tableModel.getValueAt(p.y, p.x + 1);
    return o;
  }
  private Object resolveSum(ApplicationTableModel tableModel)
  {
    String[] operands = formula.split("\\+");

    // caso 1: entrambi gli operandi rappresentano delle coordinate
    // Esempio: =A0+A1, =B0+A1 ...
    if(
        ApplicationCoordinate.isValid(operands[0]) &&
            ApplicationCoordinate.isValid(operands[1]))
    {
      ApplicationCell c1 = new ApplicationCell(
          new ApplicationCoordinate(operands[0]), tableModel);

      ApplicationCell c2 = new ApplicationCell(
          new ApplicationCoordinate(operands[1]), tableModel);

      // se entrambe le celle contengono valori numerici
      // ritorno la somma dei due valori
      if(c1.isNumeric() && c2.isNumeric())
        return ApplicationUtilities.sum(
            Double.parseDouble(c1.getData().toString()),
            Double.parseDouble(c2.getData().toString()));

      // se una delle due celle contiene un valore non numerico
      // ritorno la concatenazione testuale delle due
      return ApplicationUtilities.sum(c1.getData().toString(), c2.getData().toString());
    }

    // caso 2: se entrambi gli operandi non sono coordinate
    // ma sono valori numerici/testuali
    // Esempio: =12+3, =3+21 ...
    else if (
        !ApplicationCoordinate.isValid(operands[0]) &&
            !ApplicationCoordinate.isValid(operands[1]))
    {
      // se entrambi sono valori numerici li sommo
      if(
          ApplicationUtilities.stringNumeric(operands[0]) &&
              ApplicationUtilities.stringNumeric(operands[1]))
        return ApplicationUtilities.sum(
            Double.parseDouble(operands[0]),
            Double.parseDouble(operands[1]));

      // altrimenti li concateno
      return ApplicationUtilities.sum(operands[0], operands[1]);
    }

    // caso 3: sono uno dei due operandi rappresenta una coordinata
    // mentra l'altro rappresenta una valore numerico/testuale
    // Esempio: =A0+2, =32+B1, =hello+B3 ...
    else
    {
      int i = 0; // indice operando coordinata
      int j = 1; // indice operando valore numerico/testuale

      // se il secondo operando è una coordinata
      if(ApplicationCoordinate.isValid(operands[1]))
      {
        i = 1;
        j = 0;
      }

      ApplicationCell cell = new ApplicationCell(
          new ApplicationCoordinate(operands[i]), tableModel);

      // se la cella contiene un valore numerico
      // e il secondo operando contiene un valore numerico
      // ritorno la somma dei due valori.
      if(cell.isNumeric() && ApplicationUtilities.stringNumeric(operands[j]))
        return ApplicationUtilities.sum(
            Double.parseDouble(cell.getData().toString()),
            Double.parseDouble(operands[j]));

      // Atrimenti ritorno la concatenazione testuale
      return ApplicationUtilities.sum(cell.getData().toString(), operands[j]);
    }
  }
  private Object resolveSub(ApplicationTableModel tableModel)
  {
    String[] operands = formula.split("-");

    // caso 1: entrambi gli operandi rappresentano delle coordinate
    // Esempio: =A0+A1, =B0+A1 ...
    if(
        ApplicationCoordinate.isValid(operands[0]) &&
            ApplicationCoordinate.isValid(operands[1]))
    {
      ApplicationCell c1 = new ApplicationCell(
          new ApplicationCoordinate(operands[0]), tableModel);

      ApplicationCell c2 = new ApplicationCell(
          new ApplicationCoordinate(operands[1]), tableModel);

      // se entrambe le celle contengono valori numerici
      // ritorno la differenza dei due valori
      if(c1.isNumeric() && c2.isNumeric())
        return ApplicationUtilities.sub(
            Double.parseDouble(c1.getData().toString()),
            Double.parseDouble(c2.getData().toString())
        );

      // altrimenti return null: non posso sottrarre delle stringhe
      return null;
    }

    // caso 2: se entrambi gli operandi non sono coordinate
    // ma sono valori numerici
    // Esempio: =12-3, =3-21 ...
    else if (
        !ApplicationCoordinate.isValid(operands[0]) &&
            !ApplicationCoordinate.isValid(operands[1]))
    {
      // se entrambi sono valori numerici li sommo
      if(
          ApplicationUtilities.stringNumeric(operands[0]) &&
              ApplicationUtilities.stringNumeric(operands[1]))
        return ApplicationUtilities.sub(
            Double.parseDouble(operands[0]),
            Double.parseDouble(operands[1])
        );

      // altrimenti return null: non posso sottrarre delle stringhe
      return null;
    }

    // caso 3: sono uno dei due operandi rappresenta una coordinata
    // mentra l'altro rappresenta una valore numerico
    else
    {
      int i = 0; // indice operando coordinata
      int j = 1; // indice operando non coordinata

      // se il secondo operando è una coordinata
      if(ApplicationCoordinate.isValid(operands[1]))
      {
        i = 1;
        j = 0;
      }

      ApplicationCell cell = new ApplicationCell(
          new ApplicationCoordinate(operands[i]), tableModel);

      // se la cella contiene un valore numerico
      // e il secondo operando contiene un valore numerico
      // ritorno la somma dei due valori.
      if(cell.isNumeric() && ApplicationUtilities.stringNumeric(operands[j]))
      {
        // Esempio: =A0-4, =C0-12.4
        if(i == 0)
          return ApplicationUtilities.sub(
              Double.parseDouble(cell.getData().toString()),
              Double.parseDouble(operands[j]));

          // Esempio: =21-B2, =2-C0
        else
          return ApplicationUtilities.sub(
              Double.parseDouble(operands[j]),
              Double.parseDouble(cell.getData().toString()));
      }
      return null;
    }
  }

  private EOperations operation()
  {
    if(formula.contains("+")) return EOperations.SUM;
    if(formula.contains("-")) return EOperations.SUB;
    return EOperations.ASSIGNMENT;
  }
}

