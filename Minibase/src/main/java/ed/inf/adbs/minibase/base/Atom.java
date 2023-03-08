package ed.inf.adbs.minibase.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Atom {
    // NOTICE: New added function
    public boolean compareAtom(Atom x, Atom y){
        String nameX = ((RelationalAtom)x).getName();
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        String nameY = ((RelationalAtom)y).getName();
        List<Term> termsY = ((RelationalAtom)y).getTerms();
        if (nameX != nameY){
            return false;
        }
        if (termsX.size() != termsY.size() ){
            return false;
        }
        for (int i = 0; i < termsX.size(); i++){
            if (!termsX.get(i).toString().equals(termsY.get(i).toString())){
                return false;
            }
        }
        return true;
    }

    // NOTICE: New added function
    public boolean containVariable(Atom x){
        String nameX = ((RelationalAtom)x).getName();
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        for (Term term : termsX){
            if (term.getClass().getSimpleName().equals("Variable")){
                return true;
            }
        }
        return false;
    }

    // NOTICE: New added function
    public String getRelationName(Atom x) {
        return ((RelationalAtom)x).getName();
    }

    // NOTICE: New added function
    public int getSize(Atom x){
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        return termsX.size();
    }

    // NOTICE: New added function
    public boolean whether_Distinguished_variable(List<Variable> dis_variables, Term v){
        for (Variable dis_v : dis_variables){
            if (dis_v.toString().equals(v.toString())){
                return true;
            }
        }
        return false;
    }

    // NOTICE: New added function
    public boolean compare_Distinguished_variable(List<Variable> dis_variables, Atom x, Atom y){
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        List<Term> termsY = ((RelationalAtom)y).getTerms();
        for (int i = 0; i < termsX.size(); i++){
            if (whether_Distinguished_variable(dis_variables, termsX.get(i)) ){
                if (!whether_Distinguished_variable(dis_variables, termsY.get(i))){
                    return false;
                }
                if (termsX.get(i).toString().equals(termsY.get(i).toString())){
                    continue;
                } else{
                    return false;
                }
            } else{
                continue;
            }
        }
        return true;
    }

    public boolean whether_constant(Term t){
        return t instanceof Constant;
    }

    public boolean compare_constant(Atom x, Atom y){
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        List<Term> termsY = ((RelationalAtom)y).getTerms();
        for (int i = 0; i < termsX.size(); i++) {
            if (whether_constant(termsX.get(i)) ){
                if (!whether_constant(termsY.get(i))){
                    return false;
                }
                if (termsX.get(i).toString().equals(termsY.get(i).toString())){
                    continue;
                } else{
                    return false;
                }
            } else{
                continue;
            }
        }
        return true;

    }

    public void addKeyValue(HashMap<Term, Term> h, Atom x, Atom y){
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        List<Term> termsY = ((RelationalAtom)y).getTerms();
        for (int i = 0; i < termsX.size(); i++) {
            if (termsX.get(i).getClass().getSimpleName().equals("Variable")){
                h.put(termsX.get(i), termsY.get(i));
            }
        }
    }

    public Atom deepCopy(Atom x){
        String nameX = ((RelationalAtom)x).getName();
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        List<Term> copiedList = new ArrayList<Term>();
        for (Term t : termsX){
            Term copiedT = t.deepCopy();
            copiedList.add(copiedT);
        }

        return new RelationalAtom(nameX, copiedList);
    }

    public void substitue(HashMap<Term, Term> h, Atom x){
        List<Term> termsX = ((RelationalAtom)x).getTerms();
        for (int i = 0; i < termsX.size(); i++){
            if (termsX.get(i).getClass().getSimpleName().equals("Variable")){
                if (h.get(termsX.get(i)) != null){
                    Term t = (Term)h.get(termsX.get(i));
                    termsX.set(i, t);
                }
            }

        }
    }

}
