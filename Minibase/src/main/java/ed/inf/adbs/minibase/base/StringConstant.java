package ed.inf.adbs.minibase.base;

public class StringConstant extends Constant {
    private String value;

    public StringConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }

    public StringConstant deepCopy(){
        return new StringConstant(this.value);
    }

    @Override
    public int hashCode(){
        return this.value.hashCode();
    }

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
        if (!(obj instanceof StringConstant)) {
            return false;
        }

        if (this.value.equals(((StringConstant)obj).value)){
            return true;
        }

        return false;
    }
}