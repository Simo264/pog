import java.awt.*;

/**
 * La class ApplicationFormula rappresenta una formula nel tipo String.
 *
 * Una formula è rappresentata dal seguente pattern:
 * <coordinata/valore>[+-]<coordinata/valore>
 * Esempi: =A0, =12+A0, =12+12, =A0+A1...
 *
 *
 * Ammette 3 operazioni: assegnazione, somma e sottrazione.
 * 1. Assegnazione: ammette un solo operando di tipo coordinata o un valore numerico/testuale
 *    Esempi: =A0, =hello, =12, =B12
 *
 * 2. Somma: ammette due operandi che possono essere di tipo coordinata o valori numerici/testuali
 *    Esempi: =A0+B0, =12+A0, =12+12, =hello+A0 =hello+world ...
 *
 * 3. Sottrazione: simile alla somma con la differenza che non ammette valori testuali.
 */
public class ApplicationFormula
{
  private enum EOperations { SUM, SUBTRACT, ASSIGNMENT };
  private String formula;
  private String[] operands;
  private EOperations formulaOP;


  ApplicationFormula(String formula)
  {
    this.formula = formula;
    formulaOP = getFormulaOP();
    operands = splitOperands();
  }

  /**
   * Viene risolta la formula e viene ritornato il risultato
   * @param tableModel
   * @return String
   */
  public String resolveFormula(ApplicationTableModel tableModel)
  {
    switch (formulaOP)
    {
      case SUBTRACT ->
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

  @Override
  public String toString() { return formula; }



  private EOperations getFormulaOP()
  {
    if(formula.contains("+")) return EOperations.SUM;
    if(formula.contains("-")) return EOperations.SUBTRACT;
    return EOperations.ASSIGNMENT;
  }
  private String[] splitOperands()
  {
    if(formulaOP == EOperations.SUBTRACT)
      return formula.split("-");

    else if (formulaOP == EOperations.SUM)
      return formula.split("\\+");

    else
      return null;
  }
  private boolean areBothCoordinates(String s1, String s2)
  {
    return ApplicationCoordinate.isValid(s1) &&
        ApplicationCoordinate.isValid(s2);
  }
  private boolean areBothNumerics(String s1, String s2)
  {
    return ApplicationUtilities.isNumeric(s1) &&
        ApplicationUtilities.isNumeric(s2);
  }
  private String resolveAssignment(ApplicationTableModel tableModel)
  {
    if(!ApplicationCoordinate.isValid(formula))
      return null;

    Point p = new ApplicationCoordinate(formula).reverse();
    return tableModel.getValueAt(p.y, p.x + 1).toString();
  }
  private String resolveSum(ApplicationTableModel tableModel)
  {
    // caso 1: entrambi gli operandi rappresentano delle coordinate
    // Esempio: =A0+A1, =B0+A1 ...
    if(areBothCoordinates(operands[0], operands[1]))
    {
      Point p1 = new ApplicationCoordinate(operands[0]).reverse();
      Point p2 = new ApplicationCoordinate(operands[1]).reverse();

      String s1 = tableModel.getValueAt(p1.y, p1.x).toString();
      String s2 = tableModel.getValueAt(p2.y, p2.x).toString();

      //Se sono entrambi valori numerici li considero come Double e non come String
      if(ApplicationUtilities.isNumeric(s1) && ApplicationUtilities.isNumeric(s2))
        return String.valueOf(ApplicationUtilities.sum(
              Double.parseDouble(s1), Double.parseDouble(s2)
            ));

      //Altrimenti li concateno
      return ApplicationUtilities.sum(s1, s2);
    }

    // caso 2: se entrambi gli operandi non sono coordinate
    // ma sono valori numerici/testuali
    // Esempio: =12+3, =3+21 ...
    else if(!areBothCoordinates(operands[0], operands[1]))
    {
      // se sono entrambi numeri li sommo
      if(areBothNumerics(operands[0], operands[1]))
        return String.valueOf(ApplicationUtilities.sum(
            Double.parseDouble(operands[0]),
            Double.parseDouble(operands[1])
        ));
      // altrimenti li concateno
      return ApplicationUtilities.sum(operands[0], operands[1]);
    }

    // caso 3: sono uno dei due operandi rappresenta una coordinata
    // mentra l'altro rappresenta una valore numerico/testuale
    // Esempio: =A0+2, =32+B1, =hello+B3 ...s
    else
    {
      int i = 0; // i = indice operando coordinata
      int j = 1; // j = indice operando valore numerico/testuale

      // se il secondo operando è una coordinata
      if(ApplicationCoordinate.isValid(operands[1]))
      {
        i = 1;
        j = 0;
      }

      Point p = new ApplicationCoordinate(operands[i]).reverse();
      String s = tableModel.getValueAt(p.y, p.x).toString();

      // se sono entrambi valori numerici li sommo
      if(areBothNumerics(operands[j], s))
        return String.valueOf(ApplicationUtilities.sum(
          Double.parseDouble(operands[j]),
          Double.parseDouble(s)
        ));


      // altrimenti li concateno
      return ApplicationUtilities.sum(operands[j], s);
    }
  }
  private String resolveSub(ApplicationTableModel tableModel)
  {
    // caso 1: entrambi gli operandi rappresentano delle coordinate
    // Esempio: =A0-A1, =B0-A1 ...
    if(areBothCoordinates(operands[0], operands[1]))
    {
      Point p1 = new ApplicationCoordinate(operands[0]).reverse();
      Point p2 = new ApplicationCoordinate(operands[1]).reverse();

      String s1 = tableModel.getValueAt(p1.y, p1.x).toString();
      String s2 = tableModel.getValueAt(p2.y, p2.x).toString();

      //Se sono entrambi valori numerici li considero come Double e non come String
      if(ApplicationUtilities.isNumeric(s1) && ApplicationUtilities.isNumeric(s2))
        return String.valueOf(ApplicationUtilities.subtract(
            Double.parseDouble(s1), Double.parseDouble(s2)
        ));

      //Altrimenti ritorno un messaggio di errore
      return "Type error";
    }

    // caso 2: se entrambi gli operandi non sono coordinate
    // ma sono valori numerici/testuali
    // Esempio: =12-3, =3-21 ...
    else if(!areBothCoordinates(operands[0], operands[1]))
    {
      // se sono entrambi numeri li sottraggo
      if(areBothNumerics(operands[0], operands[1]))
        return String.valueOf(ApplicationUtilities.subtract(
            Double.parseDouble(operands[0]),
            Double.parseDouble(operands[1])
        ));

      // altrimenti ritorno un messaggio di errore
      return "Type error";
    }

    // caso 3: sono uno dei due operandi rappresenta una coordinata
    // mentra l'altro rappresenta una valore numerico/testuale
    // Esempio: =A0-2, =32-B1, =hello-B3 ...
    else
    {
      int i = 0; // i = indice operando coordinata
      int j = 1; // j = indice operando valore numerico/testuale

      // se il secondo operando è una coordinata
      if(ApplicationCoordinate.isValid(operands[1]))
      {
        i = 1;
        j = 0;
      }

      Point p = new ApplicationCoordinate(operands[i]).reverse();
      String s = tableModel.getValueAt(p.y, p.x).toString();

      // se sono entrambi valori numerici li sottraggo
      if(areBothNumerics(operands[j], s))
        return String.valueOf(ApplicationUtilities.subtract(
            Double.parseDouble(operands[j]),
            Double.parseDouble(s)
        ));


      // altrimenti ritorno un messaggio di errore
      return "Type error";
    }
  }
}
