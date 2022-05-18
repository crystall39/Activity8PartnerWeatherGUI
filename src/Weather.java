public class Weather {
    private double tempF;
    private double tempC;
    private String currentCondition;
    private String conditionIcon;

    public Weather(double tempF, double tempC, String currentCondition, String conditionIcon)
    {
        this.tempF = tempF;
        this.tempC = tempC;
        this.currentCondition = currentCondition;
        this.conditionIcon = conditionIcon;
    }

    public double getTempF()
    {
        return tempF;
    }

    public double getTempC()
    {
        return tempC;
    }

    public String getCurrentCondition()
    {
        return currentCondition;
    }

    public String getConditionIcon()
    {
        return conditionIcon;
    }
}

