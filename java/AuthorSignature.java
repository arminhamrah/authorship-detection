public class AuthorSignature
{
    private String authorName;
    private double averageWordLength;
    private double differentWordRatio;
    private double hapaxRatio;
    private double averageWordsPerSentence;
    private double averagePhrasesPerSentence;

    public AuthorSignature(String an, double awl, double dwr, double hr, double aws, double aps)
    {
        authorName = an;
        averageWordLength = awl;
        differentWordRatio = dwr;
        hapaxRatio = hr;
        averageWordsPerSentence = aws;
        averagePhrasesPerSentence = aps;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public double getAverageWordLength()
    {
        return averageWordLength;
    }

    public double getDifferentWordRatio()
    {
        return differentWordRatio;
    }

    public double getHapaxRatio()
    {
        return hapaxRatio;
    }

    public double getAverageWordsPerSentence()
    {
        return averageWordsPerSentence;
    }

    public double getAveragePhrasesPerSentence()
    {
        return averagePhrasesPerSentence;
    }
}