class Price{
    private String name;
    CalculateValueStrategy strategy;
    double price;

    public String getName(){
        return name;
    }

    public void setStrategy(CalculateValueStrategy strategy){
        this.strategy = strategy;
    }

    public double getValue(int number){
        double value = 0.0;
        try{
            value = this.strategy.getValue(this.price, number);
        } catch(Exception e){
            System.out.println(name+e);
        }
        System.out.println(name + "'s value calculated " + this.strategy);
        return value;
    }

    public double getValue(double quantity){
        double value = 0.0;
        try{
            value = this.strategy.getValue(this.price, quantity);
        } catch(Exception e){
            System.out.println(name+e);
        }
        System.out.println(name + "'s value calculated '" + this.strategy);
        return value;
    }

    public Price(String name, CalculateValueStrategy strategy, double price){
        this.name = name;
        this.strategy = strategy;
        this.price = price;
    }
}

class CalculateValueStrategy{
    private int myClass; //0 - discrete //1 - continuous
    private DiscreteStrategy ds = null;
    private ContinuousStrategy cs = null;

    public double getValue(double price, int number) throws Exception{
        if(myClass != 0){
            throw new Exception("This is a discrete item!");
        }
        return this.ds.calculateValue(price, number);
    }

    public double getValue(double price, double quantity) throws Exception{
        if(myClass != 1){
            throw new Exception("This is a continuous item!");
        }
        return this.cs.calculateValue(price, quantity);
    }

    public CalculateValueStrategy(DiscreteStrategy ds){
        this.ds = ds;
        this.myClass = 0;
    }

    public CalculateValueStrategy(ContinuousStrategy cs){
        this.cs = cs;
        this.myClass = 1;
    }

    public String toString(){
        if(myClass == 0){
            return this.ds.toString();
        } else {
            return this.cs.toString();
        }
    }
}

interface DiscreteStrategy{
    public double calculateValue(double price, int number);
}

interface ContinuousStrategy{
    public double calculateValue(double price, double quantity);
}

class CalculateBasicDiscreteStrategy implements DiscreteStrategy{
    public double calculateValue(double price, int number){
        return price*number;
    }
}

class CalculateBasicContinuousStrategy implements ContinuousStrategy{
    public double calculateValue(double price, double quantity){
        return (int)(price*quantity*100)/100.0;
    }

    public String toString(){
        return "using \'basic continuous\' strategy";
    }
}

class CalculateBuyXGetYStrategy implements DiscreteStrategy{
    int x;
    int y;
    public CalculateBuyXGetYStrategy(int x,int y){
        this.x = x;
        this.y = y;
    }

    private int getActualNumber(int number){
        return (int)Math.ceil((double)number/(x+y))*x;
    }

    public double calculateValue(double price, int number){
        return getActualNumber(number)*price;
    }

    public String toString(){
        return "using \'buying " + this.x + " and getting " + this.y + "\' strategy";
    }
}

class CalculateBuyXTogether implements DiscreteStrategy{
    int x;
    public CalculateBuyXTogether(int x){
        this.x = x;
    }

    private int getActualNumber(int number){
        return (int)Math.ceil((double)number/x);
    }

    public double calculateValue(double price, int number){
        return getActualNumber(number)*price;
    }

    public String toString(){
        return "using \'buying " + this.x + " together\' strategy";
    }
}

class main{
    public static void main(String[] args){
        Price cans = new Price("Cans", new CalculateValueStrategy(new CalculateBuyXGetYStrategy(2,1)), 1.0);
        System.out.println(cans.getValue(100));

        Price bananas = new Price("Banana", new CalculateValueStrategy(new CalculateBasicContinuousStrategy()), 5.0);
        System.out.println(bananas.getValue(0.5));

        Price socks = new Price("Socks", new CalculateValueStrategy(new CalculateBuyXTogether(4)), 10.5);
        System.out.println(socks.getValue(30));

    }

}
