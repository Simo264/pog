public class ApplicationCellFormula extends ApplicationCell <String>
{
  private ApplicationFormula applicationFormula;
  private ApplicationTableModel tableModel;


  ApplicationCellFormula(String formula)
  {
    super(formula);
    applicationFormula = new ApplicationFormula(formula);
  }

  public String resolve()
  {
    return applicationFormula.resolveFormula(tableModel);
  }
  public void setTableModel(ApplicationTableModel tableModel)
  {
    this.tableModel = tableModel;
  }
}

