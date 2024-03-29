import java.util.ArrayList;

public class SATVariable {
    //Class representing our SATVariable for fluent and action

    private int timeStep;
    private int name;
    private boolean fluent; //True if fluent and false if action

    private ArrayList<Integer> precond;
    private ArrayList<Integer> effectPlus;
    private ArrayList<Integer> effectMinus;

    public SATVariable(int timeStep, int name, boolean fluent){
        this.timeStep=timeStep;
        this.name=name;
        this.fluent=fluent;
        precond=new ArrayList<>();
        effectPlus=new ArrayList<>();
        effectMinus=new ArrayList<>();
    }

    public int getName(){
        return name;
    }

    public int getTimeStep(){
        return timeStep;
    }

    public void addPrecond(int pre){
        precond.add(pre);
    }

    public void addEffectPlus(int effPlus){
        effectPlus.add(effPlus);
    }

    public void addEffectMinus(int effMinus){
        effectMinus.add(effMinus);
    }

    public boolean isFluent(){return fluent;}

    //Used for contains()
    public boolean equals(SATVariable var2){
        return super.equals(var2) || this.name==var2.name;
    }

    //Geters for precond and effects

    public ArrayList<Integer> getPrecond(){return precond;}
    public ArrayList<Integer> getPosEffect(){return effectPlus;}
    public ArrayList<Integer> getNegEffect(){return effectMinus;}

    @Override
    public String toString(){
        String r = ""+name;
        r=r+"\n";
        r = r+"Precond :";
        for(int i : precond) r=r+i+",";
        r=r+"\n";
        r = r+"PosEffect :";
        for(int i : effectPlus) r=r+i+",";
        r=r+"\n";
        r = r+"NegEffect :";
        for(int i : effectMinus) r=r+i+",";
        r=r+"\n";
        return r;
    }
}
