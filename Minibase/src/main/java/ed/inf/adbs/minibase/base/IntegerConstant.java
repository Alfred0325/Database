package ed.inf.adbs.minibase.base;

public class IntegerConstant extends Constant {
    private Integer value;

    public IntegerConstant(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public IntegerConstant deepCopy(){
        return new IntegerConstant(this.value);
    }

    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof IntegerConstant)) {
            return false;
        }

        if (this.value == ((IntegerConstant)obj).value){
            return true;
        }

        return false;
    }
}
