public class Formula
{
    private String delimiters = "-|\\+|\\*|\\/";
    private String rawFormula;
    private Coordinate c1;
    private Coordinate c2;

    Formula(String formula)
    {
        rawFormula = formula.substring(1);
    }

    public Coordinate getC1()
    {
        Coordinate c = new Coordinate(rawFormula.split(delimiters)[0]);
        return c;
    }
    public Coordinate getC2()
    {
        Coordinate c = new Coordinate(rawFormula.split(delimiters)[1]);
        return c;
    }

    public void resolveFormula()
    {
    }
}
