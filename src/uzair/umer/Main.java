/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzair.umer;
    
/**
 *
 * @author umer
 */

import java.io.FileWriter;
import java.io.IOException;
 import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.tartarus.snowball.ext.englishStemmer;
public class Main {
    
        public static double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
             if (s.equalsIgnoreCase(termToCheck)) {
                  count++;
              }
        }
        return 1+Math.log10(count);
    }
     
     public static double idfCalculator( ArrayList<ArrayList<String>> allTerms, String termToCheck,int total) {
        double count = 0;
        double count1=0;
              
        for(int i=0 ; i<allTerms.size() ;i++)
        { 
            for(int j=0 ; j<allTerms.get(i).size() ;j++)
            {
                if(allTerms.get(i).get(j).equalsIgnoreCase(termToCheck))
                {
                    count1++;
                }            
            }
        }
        return  Math.log10( total/ count1);
    }
   
     private void createFile(String file, ArrayList<String> arrData)
            throws IOException {
        FileWriter writer = new FileWriter(file + ".txt");
        int size = arrData.size();
        for (int i=0;i<size;i++) {
            String str = arrData.get(i).toString();
            writer.write(str);
            if(i < size-1)//This prevent creating a blank like at the end of the file**
                writer.write("\n");
        }
        writer.close();
    }
    
    public static double cosineSimilarity(Double[] doc1, Double[] doc2) {
        double temp;
    int doc1Len = doc1.length;
    int doc2Len = doc2.length;
    float numerator = 0;
    float temSumDoc1 = 0;
    float temSumDoc2 = 0;
    double equlideanNormOfDoc1 = 0;
    double equlideanNormOfDoc2 = 0;
    if (doc1Len > doc2Len) {
        for (int i = 0; i < doc2Len; i++) {
            numerator += doc1[i] * doc2[i];
            temSumDoc1 += doc1[i] * doc1[i];
            temSumDoc2 += doc2[i] * doc2[i];
        }
        equlideanNormOfDoc1=Math.sqrt(temSumDoc1);
         equlideanNormOfDoc2=Math.sqrt(temSumDoc2);
    } else {
        for (int i = 0; i < doc1Len; i++) {
            numerator += doc1[i] * doc2[i];
            temSumDoc1 += doc1[i] * doc1[i];
            temSumDoc2 += doc2[i] * doc2[i];
        }
         equlideanNormOfDoc1=Math.sqrt(temSumDoc1);
         equlideanNormOfDoc2=Math.sqrt(temSumDoc2);
    }

    temp = numerator / (equlideanNormOfDoc1 * equlideanNormOfDoc2);
    return temp;
    }
     
public static void main(String[] args) throws IOException {
    // TODO code application logic here

// breaking all the sentences

String[] stopwords = {"a", "as", "able", "about",
"above", "according", "accordingly", "across", "actually",
"after", "afterwards", "again", "against", "aint", "all",
"allow", "allows", "almost", "alone", "along", "already",
"also", "although", "always", "am", "among", "amongst", "an",
"and", "another", "any", "anybody", "anyhow", "anyone", "anything",
"anyway", "anyways", "anywhere", "apart", "appear", "appreciate",
"appropriate", "are", "arent", "around", "as", "aside", "ask", "asking",
"associated", "at", "available", "away", "awfully", "be", "became", "because",
"become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being",
"believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both",
"brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes",
"certain", "certainly", "changes", "clearly", "co", "com", "come",
"comes", "concerning", "consequently", "consider", "considering", "contain",
"containing",    "contains","corresponding","could", "couldnt", "course", "currently",
"definitely", "described", "despite", "did", "didnt", "different", "do", "does",
"doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu",
"eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially",
"et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere",
"ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed",   
"following", "follows", "for", "former", "formerly", "forth", "four", "from", "further",
"furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone"
    , "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have",
    "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
 

ArrayList<String> DuplicateStringLists = new ArrayList<String>();
//ArrayList<double> tfidf= new ArrayList<double>();
  ArrayList<ArrayList<String>> wordsList = new ArrayList<>();   
 //Cricket Match
  String passage="In Pakistan, cricket is the most popular game. National team of Pakistan is among the top team of the world. Large amount of people including children and women watch cricket matches.\n" +
"\n" +
"One of the most interesting matches of my life was when our school team played the inter-school final. On November 1, our school team played the final with last’s year interschool champion team. We all were very exciting. Our team won the toss and our captain elected to bat first. The weather was cloudy that day. At around 10 pm, the match started. Our team played and scored 180 in 20 overs. It was a good innings and amazing to watch.\n" +
"\n" +
"The second innings started after lunch. The rival team’s openers were great and scored 120 in the start. We all thought that we would definitely loss the match but turning point came when one of our fast bowler took the wicket of the opener and that was the point we headed towards victory. After that, every player contributed single digit score and their whole team was out at the 160.\n" +
"\n" +
"It was a great victory to enjoy. Large numbers of spectators had also enjoyed the match. We celebrated our win with great fun.";

 //Essay on computer Science 
 /*
 String passage="  Computer science is one of the fastest growing career fields in modern \n" +
"             history. Dating back only a few decades to the late 1950s and early 1960s, it \n" +
"             has become one of the leading industries in the world today. Developed \n" +
"             through the technological architecture of electrical engineering and the \n" +
"             computational language of mathematics, the science of computer technology \n" +
"             has provided considerable recognition and financial gain for many of its well \n" +
"             deserving pioneers. Originally conceived as an organizational solution to the \n" +
"             massive amounts of information kept on nothing more than paper, computers \n" +
"             have evolved and advanced to become a common part of modern day life. In \n" +
"             the early days of the computers age, the newest and most complex \n" +
"             computers took up no less than an entire building or very large room. It was \n" +
"             inconceivable that these machines would after only about fifty years be \n" +
"             many times more powerful and small enough to be held with tweezers \n" +
"             With the introduction of miniature circuitry and transistors, the days of\n" +
"             vacuum tubes and computers that filled entire buildings are long gone and \n" +
"             the centerpiece of the today’s computer industry is the CPU, one of the \n" +
"             modern marvels of science and technology. It is essentially the brain of the \n" +
"             computer and though it is the main determining factor in the processing \n" +
"             power of the computer as a whole, many other parts of the machine are just \n" +
"             as important in overall performance. Many people don’t know this and that \n" +
"             is how computer corporations have cheated people out of their money for so \n" +
"             many years by selling them cheap systems with high megahertz numbers for \n" +
"             the processors in them. This is one reason for the success of the computer \n" +
"             industry. When people find out that they have been cheated, they will try to ";
    */
    //my hobby 
 /*
 String passage="We all do some kind of a work either to earn our live hood or to make a career. Hobby is something which we enjoy doing, we like indulging ourselves in the activity during our leisure or free time. We all have our likes and dislike. We enjoy doing something more than others. A hobby gives us pleasure for we do it, for the love of the work and not under compulsion to earn. Thus, it is more fulfilling and gives us more satisfaction and joy.\n" +
"Pursuing a hobby also increase one's efficiency, interest and ability. It gives an opportunity to full development of one's various aspects of personality. Hobbies like collecting stamps, listening music, drawing, gardening, playing an indoor or outdoor sport, writing, reading, bird watching, collecting antiques, photography etc, are very educative. We learn many things with practical insights which we cannot learn from.\n" +
"One of the favourite things that 1 enjoy doing is gardening. I like the joy of beholding a blooming garden, a green lawn and greener plants at home. Therefore, it has become my hobby now.\n" +
"I caught this habit of nurturing plants from my mother. Now with her help and my renewed interest we have managed to maintain a small garden in front of our porch. It has a green carpet of velvet grass and a small trimmed hedge growing around it.\n" +
"We have also prepared flowerbeds in which we have planted a few rose bushes, lilies, sunflowers, mogra, China rose and colourful variety of seasonal flowers. We also have grown gladioli, orchids, chrysanthemums, germaniums, jasmines, ferns and crotons. Recently, bought Christmas tree.\n" +
"Everyday after 1 return from my school and have my lunch, 1 rush to the garden to do a little digging and watering the plants. We also regularly add manure and other medicines for protection and better flowering. During autumn one has to clean the garden daily because of the number of dry leaves shed. Otherwise, I clean it only twice a week. Grass grows very quickly during the rainy seasons, so one has to mow it regularly.\n" +
"Moreover, every day before going to school, I check the kind of flowers blooming and the number of buds ready to bloom. On birthdays and anniversaries, I sometimes make a bouquet from my garden as a present.\n" +
"Now I am thinking to grow vegetables also in one corner of the garden. If I succeed, then we won’t have to buy vegetables and we can even distribute it among our neighbours and friends. At least the common ones can be grown which occupay less space and are eaten every other day.\n" +
"Gardening is a good hobby. I really enjoy it. When others appreciate and admire our garden I feel very proud of my efforts. It is a great joy to behold the small garden in full bloom. It is not so expensive also and increases the beauty of my home. Gardening also keeps me fit and healthy. I really enjoy gardening.";
 */
 

 String[] s = passage.split("[.;?;]");
  //String[] s2 = passage.split("\\s+");
String[] s1 = new String[s.length];
  String[] words=passage.split("\\s+"); ;
  englishStemmer english = new englishStemmer();
  String[] tokens=new String[100000];
   ArrayList<ArrayList<Double>> tfidf = new ArrayList<>();
   
  int Size=0;
  
System.out.println("\n" + "----STEMMED DOCUEMENTS---- \n" );

 for(int i=0 ; i<s.length ;i++)
 {
    words= s[i].split(" ");
        
        //making new Array list
     wordsList.add(new ArrayList<String>());
    
    for ( int j=0; j<words.length;j++) {
        //stemming 
        //if(words[j].matches("\\b(" + StringUtils.join(tokens, "|") + ")\\b"))
        
        //stemming
        
        english.setCurrent(words[j]);
        english.stem();
        
        
        wordsList.get(i).add(english.getCurrent());
           
        }
  String[] words1=new String[wordsList.get(i).size()];
   
    for(int m=0 ; m<wordsList.get(i).size() ;m++)
    {
    
    
    words1[m]=wordsList.get(i).get(m);
    
    
    }
    Size=Size+wordsList.get(i).size();
    //printing each docuement
     System.out.println("\n"+"Doc "+i+":"+wordsList.get(i));
}
     
System.out.println(Size);

double[][] score=new double[s.length][s.length] ;

System.out.println("\n" + "----TF-IDF OF EACH DOCUEMENT WORDS---- \n" );

// --- tf-idf

for(int i=0 ; i<s.length ;i++)
{
    tfidf.add(new ArrayList<Double>());
    System.out.println("doc"+"="+i+"\n");
    
    for(int j=0 ;j<wordsList.get(i).size() ;j++)
    {
//finding tf-idf of each word
   tfidf.get(i).add(j,tfCalculator(wordsList.get(i).toArray(new String[wordsList.get(i).size()]),wordsList.get(i).get(j)) * idfCalculator(wordsList,wordsList.get(i).get(j),Size));
    
    //printing tf-idf of each word
    System.out.println(tfidf.get(i).get(j));
   
    }
     
}

//cosine similioraity

System.out.println("\n ---- COSINE SIMILARITY ---- \n");


for(int m=0 ; m<s.length ;m++)
{
        Double[] d = tfidf.get(m).toArray(new Double[tfidf.get(m).size()]);
        score[m][1]=m;
    
    System.out.println("for doc: " + m + "\n" );
    
            for(int n=0 ; n<s.length ;n++)
        { 
            Double[] d1 = tfidf.get(n).toArray(new Double[tfidf.get(n).size()]);
        
        //finding cosine similarity and add similarity of each docuement
        
        System.out.println("for doc" + m + " to"  + "doc" + n + ":" );
        System.out.println(cosineSimilarity(d1 ,d));
           score[m][0]=score[m][0]+ cosineSimilarity(d1 ,d);
       
        }
     
System.out.println("\n add all similarities for doc" + m + ":" );
    System.out.println(score[m][0]);
}

double temp;
        for(int i = 0; i < score.length; ++i){
            for(int j = i + 1; j < score.length; ++j){
                if(score[j][0] > score[i][0]){
                    temp = score[i][0];
                    score[i][0] = score[j][0];
                    score[j][0] = temp;
                    temp = score[i][1];
                    score[i][1] = score[j][1];
                    score[j][1] = temp;
                }
            }
        }
        
    for(int i = 0; i < score.length; ++i)
    {
        System.out.println("\n rank : " + i + "\n");
        System.out.println("doc : "+score[i][1] + " similarity :  " + score[i][0]);
    }

java.util.Arrays.sort(score, new java.util.Comparator<double[]>() {
    @Override
    public int compare(double[] o1, double[] o2) {
        // note that o2 comes first here to sort in descending order
        return Double.compare(o2[0], o1[0]);
    }
});
// making score variable in which each score has an overall sinmilarity score and a rank of that docuement

    for(int i = 0; i < score.length; ++i){
        System.out.println("\n Similarity for doc " + score[i][1] + " is " + score[i][0]);
    }

System.out.println("\n ORIGIONAL DOCUEMENT \n");

for(int r=0 ; r<s.length ;r++)
{
System.out.println(s[r]);
}
System.out.println('\n');


//printing summery
System.out.println("-----SUMMERY-----" + '\n');

for(int r=0 ; r<5 ;r++)
{
System.out.println(s[(int)score[r][1]]);
}

}

}// main close
                                     // End
 
 
  
 
     
   

 







