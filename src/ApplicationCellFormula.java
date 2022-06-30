public class ApplicationCellFormula extends ApplicationCell <String>
{
  private ApplicationFormula formula;
  private ApplicationTableModel tableModel;


  ApplicationCellFormula(String formula)
  {
    super(formula);
    this.formula = new ApplicationFormula(formula);
  }

  public String resolve()
  {
    return formula.resolveFormula(tableModel);
  }
  public void setTableModel(ApplicationTableModel tableModel)
  {
    this.tableModel = tableModel;
  }
}

