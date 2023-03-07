package ed.inf.adbs.minibase.base;

public class Variable extends Term {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Variable deepCopy(){
        return new Variable(this.name);
    }

    @Override
    public int hashCode() {return name.hashCode();}

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Variable)) {
            return false;
        }

        if (!this.name.equals(((Variable)obj).name)){
            return false;
        }

        return true;
    }
}
