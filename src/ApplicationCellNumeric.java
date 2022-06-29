public class ApplicationCellNumeric extends ApplicationCell <Double>
{
  ApplicationCellNumeric(Double data)
  {
    super(data);
  }

  public Double resolve(){ return Double.valueOf(getData()); }
}
