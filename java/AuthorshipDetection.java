//Armin and Jerry
import acm.program.*;
import java.util.ArrayList;

public class AuthorshipDetection extends ConsoleProgram
{
    private static final String PUNCTUATION = "'!\",;:.-?)([]<>*#\n\t\r ";
    private static final double[] WEIGHT = {11.0, 33.0, 50.0, 0.4, 4.0};
    private AuthorSignature[] authors;

    public void run()
    {
        loadAuthorSignatures();
        String filename = readLine("Enter file name: ");
        String fileContents = FileHelper.getFileContents(filename);
    }

    private ArrayList<String> getSentencesFromContents(String fileContents)   
    {
        int startIndex = 0;
        int endIndex =  -1;
        ArrayList<String> sentences = new ArrayList<String>();
        for (int i=0; i<fileContents.length(); i++)
        {
            if (fileContents.charAt(i) == '.' || fileContents.charAt(i) == '?' || fileContents.charAt(i) == '!')
            {
                startIndex = endIndex + 1;
                endIndex = i;
                sentences.add(fileContents.substring(startIndex, endIndex + 1));
            }
        }
        if (fileContents.charAt(fileContents.length()) != '.' || fileContents.charAt(fileContents.length()) != '?' || fileContents.charAt(fileContents.length()) != '!') 
        {
            startIndex = endIndex + 1;
            endIndex = fileContents.length();
            sentences.add(fileContents.substring(startIndex, endIndex + 1));
        }
        return sentences;
    }

    private ArrayList<String> getWordsFromSentence(String sentence)
    {
        ArrayList<String> words = new ArrayList<String>();
        int startIndex = 0;
        int endIndex =  -1;
        for (int j=0; j<sentence.length(); j++)
        {
            if (sentence.charAt(j) == ' ')
            {
                startIndex = endIndex + 1;
                endIndex = j;
                words.add(clean(sentence.substring(startIndex, endIndex)));
            }
        }
        return words;
    }

    private ArrayList<String> getAllWordsFromSentences(ArrayList<String> sentences)
    {
        ArrayList<String> words = new ArrayList<String>();
        for (int i=0; i<sentences.size(); i++)
        {
            ArrayList<String> wordsInSentence = getWordsFromSentence(sentences.get(i));
            for (String word: wordsInSentence)
            {
                words.add(clean(word));
            }
        }
        return words;
    }

    private String clean (String word)
    {
        word = word.toLowerCase();
        while(PUNCTUATION.indexOf(word.charAt(0)) > -1)
        {
            word = word.substring(1);
        }
        while (PUNCTUATION.indexOf(word.charAt(word.length() - 1)) > -1)
        {
            word = word.substring(0, word.length() - 1);
        }
        return word;
    }

    private double computeAverageWordLength(ArrayList<String> words)
    {
        int totalLetters = 0;
        for (String word : words)
        {
            totalLetters+=word.length();
        }
        return totalLetters/words.size();
    }

    //Armin (Jerry joined Aidan at this point since I had a couple AP exam overlaps)
    private double computeDifferentWordRatio(ArrayList<String> words)
    {
        ArrayList<String> uniqueWords= new ArrayList<String>(); boolean found= false;
        for( int i =0; i<words.size(); i++)
        {
            String thisWord = words.get(i);
            if (!uniqueWords.contains(thisWord))
            {
                uniqueWords.add(thisWord);
            }
        }
        return (uniqueWords.size() * 1.0) / words.size()*1.0;
    }

    private double computeHapaxLegomannaRatio(ArrayList<String> words)
    {
        ArrayList<String> once= new ArrayList<String>(); 
        ArrayList<String> twice= new ArrayList<String>();
        for (int i = 0; i<words.size(); i++)
        {
            if (!once.contains(words.get(i)))
            {
                once.add(words.get(i));
            }

            else if (!twice.contains(words.get(i)))
            {
                twice.add(words.get(i));
            }
        }
        return ((once.size()-twice.size())*1.0)/words.size();
    }

    private double computeAverageWordsPerSentence(ArrayList<String> sentences)
    {
        int counter= 0;
        int size = sentences.size();
        for (int i = 0; i<sentences.size(); i++)
        {
            counter+=getWordsFromSentence(sentences.get(i)).size();
        }
        return (counter*1.0)/size;
    }

    private double computeSentenceComplexity(ArrayList<String> sentences)
    {
        int counter = 0;
        int sectionCounter = 1;
        for (int i = 0; i<sentences.size(); i++)
        {
            sectionCounter = 1;
            for (int j = 0; j<sentences.get(i).length(); j++)
            {
                String sentence= sentences.get(i);
                if (sentence.charAt(j) == ',' || sentence.charAt(j) == ';'
                || sentence.charAt(j) == ':')
                {
                    sectionCounter++;
                }
            }
            counter+= sectionCounter;
        }
        return (counter*1.0)/sentences.size();
    }

    private double computeScore(AuthorSignature first, AuthorSignature second)
    {
        double score= 0;
        score+= Math.abs((first.getAverageWordLength() - second.getAverageWordLength())*WEIGHT[0]);
        score+= Math.abs((first.getDifferentWordRatio() - second.getDifferentWordRatio())*WEIGHT[1]);
        score+= Math.abs((first.getHapaxRatio() - second.getHapaxRatio())*WEIGHT[2]);
        score+= Math.abs((first.getAverageWordsPerSentence() - second.getAverageWordsPerSentence())*WEIGHT[3]);
        score+= Math.abs((first.getAveragePhrasesPerSentence() - second.getAveragePhrasesPerSentence()) *WEIGHT[4]);
        return score;
    }

    private void loadAuthorSignatures()
    {
        authors = new AuthorSignature[13];
        authors[0] = new AuthorSignature("Agatha Christie", 4.40212537354, 0.103719383127, 0.0534892315963, 10.0836888743, 1.90662947161);
        authors[1] = new AuthorSignature("Alexandre Dumas", 4.38235547477, 0.049677588873, 0.0212183996175, 15.0054854981, 2.63499369483);
        authors[2] = new AuthorSignature("Brothers Grimm", 3.96868608302, 0.0529378997714, 0.0208217283571, 22.2267197987, 3.4129614094);
        authors[3] = new AuthorSignature("Charles Dickens", 4.34760725241, 0.0803220950584, 0.0390662700499, 16.2613453121, 2.87721723105);
        authors[4] = new AuthorSignature("Douglas Adams", 4.33408042189, 0.238435104414, 0.141554321967, 13.2874354561, 1.86574870912);
        authors[5] = new AuthorSignature("Emily Bronte", 4.35858972311, 0.089662598104, 0.0434307152651, 16.1531664212, 2.93439550141);
        authors[6] = new AuthorSignature("Fyodor Dostoevsky", 4.34066732195, 0.0528571428571, 0.0233414043584, 12.8108273249, 2.16705364781);
        authors[7] = new AuthorSignature("James Joyce", 4.52346300961, 0.120109917189, 0.0682315429476, 10.9663296918, 1.79667373227);
        authors[8] = new AuthorSignature("Jane Austen", 4.41553119311, 0.0563451817574, 0.02229943808, 16.8869087498, 2.54817097682);
        authors[9] = new AuthorSignature("Lewis Caroll", 4.22709528497, 0.111591342227, 0.0537026953444, 16.2728740581, 2.86275565124);
        authors[10] = new AuthorSignature("Mark Twain", 4.33272222298, 0.117254215021, 0.0633074228159, 14.3548573631, 2.43716268311);
        authors[11] = new AuthorSignature("Sir Arthur Conan Doyle", 4.16808311494, 0.0822989796874, 0.0394458485444, 14.717564466, 2.2220872148);
        authors[12] = new AuthorSignature("William Shakespeare", 4.16216957834, 0.105602561171, 0.0575348730848, 9.34707371975, 2.24620146314);
        //add more authors
    }

}