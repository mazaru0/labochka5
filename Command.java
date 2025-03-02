public abstract class Command {
    private final String name;
    protected Command(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public abstract void execute();
    public abstract void getHelp();


}