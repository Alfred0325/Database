package ed.inf.adbs.minibase.base;

import ed.inf.adbs.minibase.Utils;

import java.util.List;

public class RelationalAtom extends Atom {
    private String name;

    private List<Term> terms;

    public RelationalAtom(String name, List<Term> terms) {
        this.name = name;
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public List<Term> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return name + "(" + Utils.join(terms, ", ") + ")";
    }

    @Override
    public int hashCode(){
        int termHashCode = 1;
        for (Term t: terms){
            termHashCode = termHashCode * t.hashCode();
        }
        return (name.hashCode()*termHashCode);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof RelationalAtom)) {
            return false;
        }

        String nameObj = ((RelationalAtom)obj).getName();
        List<Term> termsObj= ((RelationalAtom)obj).getTerms();
        if (!this.name.equals(nameObj)){
            return false;
        }

        for (int i = 0; i < terms.size(); i++){
            if (!terms.get(i).equals(termsObj.get(i))){
                return false;
            }
        }

        return true;
    }



}
