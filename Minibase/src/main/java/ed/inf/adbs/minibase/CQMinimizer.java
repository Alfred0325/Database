package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;
import ed.inf.adbs.minibase.parser.QueryParser;

import java.io.FileWriter;
import java.io.IOException;
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

    public static void writeToFile(String inputFile, Query result ){
        String[] s = inputFile.split("/");
        s[2] = "output";
        String outputPath = String.join("/", s);
        try {
            FileWriter myWriter = new FileWriter(outputPath);
            myWriter.write(result.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static boolean checkValidMap(HashMap<Term, Term> h, List<Atom> body){
        for (Atom m : body){
            Atom n = m.deepCopy(m);
            n.substitue(h, n);
            //check if n is a subset
            if(!body.contains(((RelationalAtom)n))){
                return false;
            }
        }
        return true;

    }

    public static List<Atom> ValidCQ(HashMap<Term, Term> h, List<Atom> body){
        List<Atom> subBody = new ArrayList<Atom>();
        for (Atom m : body) {
            Atom n = m.deepCopy(m);
            n.substitue(h, n);
            if(subBody.contains(((RelationalAtom)n))) {
                continue;
            } else{
                subBody.add(n);
            }
        }
        return subBody;
    }

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

                HashMap<Term, Term> h = new HashMap<Term, Term>();
                x.addKeyValue(h, x, y);

                if (!checkValidMap(h, body)){
                    continue;
                } else{
                    if (h.size() > 0){
                        List<Atom> subBody = new ArrayList<Atom>();
                        for (Atom m : body){
                            if (!((RelationalAtom)m).equals((RelationalAtom)x)){
                                subBody.add(m);
                            }
                        }
                        List<HashMap<Term, Term>> subHs = findSubstitution(dis_variables, subBody);

                        for (HashMap<Term, Term> subH : subHs){
                           h.putAll(subH);
                        }
                    }
                    if (!hs.contains(h)){
                        hs.add(h);
                    }
                }

            }
        }
        return hs;
    }


    public static void minimizeCQ(String inputFile, String outputFile) {
        try {
            Query query = QueryParser.parse(Paths.get(inputFile));
            System.out.println("Entire query: " + query);
            Head head = query.getHead();
            System.out.println("Head: " + head);
            List<Atom> body = query.getBody();
            System.out.println("Body: " + body);

            List<Variable> dis_variables = head.getVariables();
            List<HashMap<Term, Term>> hs = findSubstitution(dis_variables, body);

            List<Atom> finalCQBody = new ArrayList<Atom>();
            if (hs.size() == 0){
                finalCQBody.addAll(body);
            }
            for (HashMap<Term, Term> h : hs){
                List<Atom> CQBody = ValidCQ(h, body);
                finalCQBody.removeAll(finalCQBody);
                finalCQBody.addAll(CQBody);
            }

            Query result = new Query(head, finalCQBody);
            writeToFile(inputFile, result);
            System.out.println(result);

            // expected output
            Query expectedQuery = QueryParser.parse(Paths.get(outputFile));
            System.out.println("Expected query: " + expectedQuery);

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
