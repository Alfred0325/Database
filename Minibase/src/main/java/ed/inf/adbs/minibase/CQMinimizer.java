package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;
import ed.inf.adbs.minibase.parser.QueryParser;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Minimization of conjunctive queries
 *
 */
public class CQMinimizer {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: CQMinimizer input_file output_file");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        minimizeCQ(inputFile, outputFile);

        //parsingExample(inputFile);
    }

    public static boolean checkValidMap(HashMap<Term, Term> h, List<Atom> body){
        for (Atom m : body){
            //System.out.println(m);
            Atom n = m.deepCopy(m);
            n.substitue(h, n);
            //System.out.println(n);
        }

        return true;
    }

    /**
     * CQ minimization procedure
     *
     * Assume the body of the query from inputFile has no comparison atoms
     * but could potentially have constants in its relational atoms.
     *
     */
    public static List<HashMap<Term, Term>> findSubstitution(List<Variable> dis_variables, List<Atom> body){
        List<HashMap<Term, Term>> hs = new ArrayList();

        for (Atom x : body){
            String name = ((RelationalAtom)x).getName();
            List<Term> terms = ((RelationalAtom)x).getTerms();

            if(!x.containVariable(x)){
               continue;
            }

            for (Atom y : body){
                if (x.getSize(x) != y.getSize(y)){
                    continue;
                }
                if (!x.getRelationName(x).equals(y.getRelationName(y))) {
                    continue;
                }
                if (!x.compare_Distinguished_variable(dis_variables, x, y)){
                    continue;
                }
                if (!x. compare_constant(x, y)){
                    continue;
                }
                if (x.compareAtom(x,y)){
                    continue;
                }

                System.out.println(x);
                System.out.println(y);

                HashMap<Term, Term> h = new HashMap<Term, Term>();
                x.addKeyValue(h, x, y);
                System.out.println("<=================>");
                //System.out.println(h);
                checkValidMap(h, body);
                System.out.println("=================");
//                if (checkValidMap(body)){
//
//
//                    hs.add(h);
//                } else{
//                    continue;
//                }




            }
        }

        return hs;
    }


    public static void minimizeCQ(String inputFile, String outputFile) {
        // TODO: add your implementation

        try {
            Query query = QueryParser.parse(Paths.get(inputFile));

//            System.out.println("Entire query: " + query);
            Head head = query.getHead();
//            System.out.println("Head: " + head);
            List<Atom> body = query.getBody();
//            System.out.println("Body: " + body);

            List<Variable> dis_variables = head.getVariables();
            findSubstitution(dis_variables, body);

//            for (Atom atom : body){
//
//            }


            // expected output
//            Query expectedQuery = QueryParser.parse(Paths.get(outputFile));
//            System.out.println("Expected query: " + expectedQuery);

        }
        catch (Exception e)
        {
            System.err.println("Exception occurred during parsing");
            e.printStackTrace();
        }

    }




    /**
     * Example method for getting started with the parser.
     * Reads CQ from a file and prints it to screen, then extracts Head and Body
     * from the query and prints them to screen.
     */

    public static void parsingExample(String filename) {

        try {
             Query query = QueryParser.parse(Paths.get(filename));
             //Query query = QueryParser.parse("Q(x, y) :- R(x, z), S(y, z, w)");
            // Query query = QueryParser.parse("Q(x) :- R(x, 'z'), S(4, z, w)");

            System.out.println("Entire query: " + query);
            Head head = query.getHead();
            System.out.println("Head: " + head);
            List<Atom> body = query.getBody();
            System.out.println("Body: " + body);
        }
        catch (Exception e)
        {
            System.err.println("Exception occurred during parsing");
            e.printStackTrace();
        }
    }

}
