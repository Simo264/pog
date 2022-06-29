public class ApplicationCellString extends ApplicationCell <String>
{
  ApplicationCellString(String data)
  {
    super(data);
  }

  public String resolve()
  {
    return String.valueOf(getData());
  }
}
