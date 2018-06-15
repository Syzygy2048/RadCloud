package io.github.syzygy2048.radcloud;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DocumentManager {

    /**
     * List of documents
     */
    private HashMap<String, Integer> documentList = new HashMap<>();
    /**
     * Direction of documents
     */
    private HashMap<String, Vec2> documentVectors = new HashMap<>();
    /**
     * Collection of all words in all documents
     */
    private ArrayList<Word> wordList = new ArrayList<>();
    /**
     * Ellipse around the words
     */
    private Ellipse frame = new Ellipse();
    /**
     * Instance od Document Manager, used for singleton
     */
    private static DocumentManager instance;

    /**
     * Stopwords
     */
    private String[] stopwords = new String[]{"'ll", "'tis", "'twas", "'ve", "10", "39", "a", "a's", "able", "ableabout", "about", "above", "abroad", "abst", "accordance", "according", "accordingly", "across", "act", "actually", "ad", "added", "adj", "adopted", "ae", "af", "affected", "affecting", "affects", "after", "afterwards", "ag", "again", "against", "ago", "ah", "ahead", "ai", "ain't", "aint", "al", "all", "allow", "allows", "almost", "alone", "along", "alongside", "already", "also", "although", "always", "am", "amid", "amidst", "among", "amongst", "amoungst", "amount", "an", "and", "announce", "another", "any", "anybody", "anyhow", "anymore", "anyone", "anything", "anyway", "anyways", "anywhere", "ao", "apart", "apparently", "appear", "appreciate", "appropriate", "approximately", "aq", "ar", "are", "area", "areas", "aren", "aren't", "arent", "arise", "around", "arpa", "as", "aside", "ask", "asked", "asking", "asks", "associated", "at", "au", "auth", "available", "aw", "away", "awfully", "az", "b", "ba", "back", "backed", "backing", "backs", "backward", "backwards", "bb", "bd", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "began", "begin", "beginning", "beginnings", "begins", "behind", "being", "beings", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "bf", "bg", "bh", "bi", "big", "bill", "billion", "biol", "bj", "bm", "bn", "bo", "both", "bottom", "br", "brief", "briefly", "bs", "bt", "but", "buy", "bv", "bw", "by", "bz", "c", "c'mon", "c's", "ca", "call", "came", "can", "can't", "cannot", "cant", "caption", "case", "cases", "cause", "causes", "cc", "cd", "certain", "certainly", "cf", "cg", "ch", "changes", "ci", "ck", "cl", "clear", "clearly", "click", "cm", "cmon", "cn", "co", "co.", "com", "come", "comes", "computer", "con", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "copy", "corresponding", "could", "could've", "couldn", "couldn't", "couldnt", "course", "cr", "cry", "cs", "cu", "currently", "cv", "cx", "cy", "cz", "d", "dare", "daren't", "darent", "date", "de", "dear", "definitely", "describe", "described", "despite", "detail", "did", "didn", "didn't", "didnt", "differ", "different", "differently", "directly", "dj", "dk", "dm", "do", "does", "doesn", "doesn't", "doesnt", "doing", "don", "don't", "done", "dont", "doubtful", "down", "downed", "downing", "downs", "downwards", "due", "during", "dz", "e", "each", "early", "ec", "ed", "edu", "ee", "effect", "eg", "eh", "eight", "eighty", "either", "eleven", "else", "elsewhere", "empty", "end", "ended", "ending", "ends", "enough", "entirely", "er", "es", "especially", "et", "et-al", "etc", "even", "evenly", "ever", "evermore", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "f", "face", "faces", "fact", "facts", "fairly", "far", "farther", "felt", "few", "fewer", "ff", "fi", "fifteen", "fifth", "fifty", "fify", "fill", "find", "finds", "fire", "first", "five", "fix", "fj", "fk", "fm", "fo", "followed", "following", "follows", "for", "forever", "former", "formerly", "forth", "forty", "forward", "found", "four", "fr", "free", "from", "front", "full", "fully", "further", "furthered", "furthering", "furthermore", "furthers", "fx", "g", "ga", "gave", "gb", "gd", "ge", "general", "generally", "get", "gets", "getting", "gf", "gg", "gh", "gi", "give", "given", "gives", "giving", "gl", "gm", "gmt", "gn", "go", "goes", "going", "gone", "good", "goods", "got", "gotten", "gov", "gp", "gq", "gr", "great", "greater", "greatest", "greetings", "group", "grouped", "grouping", "groups", "gs", "gt", "gu", "gw", "gy", "h", "had", "hadn't", "hadnt", "half", "happens", "hardly", "has", "hasn", "hasn't", "hasnt", "have", "haven", "haven't", "havent", "having", "he", "he'd", "he'll", "he's", "hed", "hell", "hello", "help", "hence", "her", "here", "here's", "hereafter", "hereby", "herein", "heres", "hereupon", "hers", "herself", "herse”", "hes", "hi", "hid", "high", "higher", "highest", "him", "himself", "himse”", "his", "hither", "hk", "hm", "hn", "home", "homepage", "hopefully", "how", "how'd", "how'll", "how's", "howbeit", "however", "hr", "ht", "htm", "html", "http", "hu", "hundred", "i", "i'd", "i'll", "i'm", "i've", "i.e.", "id", "ie", "if", "ignored", "ii", "il", "ill", "im", "immediate", "immediately", "importance", "important", "in", "inasmuch", "inc", "inc.", "indeed", "index", "indicate", "indicated", "indicates", "information", "inner", "inside", "insofar", "instead", "int", "interest", "interested", "interesting", "interests", "into", "invention", "inward", "io", "iq", "ir", "is", "isn", "isn't", "isnt", "it", "it'd", "it'll", "it's", "itd", "itll", "its", "itself", "itse”", "ive", "j", "je", "jm", "jo", "join", "jp", "just", "k", "ke", "keep", "keeps", "kept", "keys", "kg", "kh", "ki", "kind", "km", "kn", "knew", "know", "known", "knows", "kp", "kr", "kw", "ky", "kz", "l", "la", "large", "largely", "last", "lately", "later", "latest", "latter", "latterly", "lb", "lc", "least", "length", "less", "lest", "let", "let's", "lets", "li", "like", "liked", "likely", "likewise", "line", "little", "lk", "ll", "long", "longer", "longest", "look", "looking", "looks", "low", "lower", "lr", "ls", "lt", "ltd", "lu", "lv", "ly", "m", "ma", "made", "mainly", "make", "makes", "making", "man", "many", "may", "maybe", "mayn't", "maynt", "mc", "md", "me", "mean", "means", "meantime", "meanwhile", "member", "members", "men", "merely", "mg", "mh", "microsoft", "might", "might've", "mightn't", "mightnt", "mil", "mill", "million", "mine", "minus", "miss", "mk", "ml", "mm", "mn", "mo", "more", "moreover", "most", "mostly", "move", "mp", "mq", "mr", "mrs", "ms", "msie", "mt", "mu", "much", "mug", "must", "must've", "mustn't", "mustnt", "mv", "mw", "mx", "my", "myself", "myse”", "mz", "n", "na", "name", "namely", "nay", "nc", "nd", "ne", "near", "nearly", "necessarily", "necessary", "need", "needed", "needing", "needn't", "neednt", "needs", "neither", "net", "netscape", "never", "neverf", "neverless", "nevertheless", "new", "newer", "newest", "next", "nf", "ng", "ni", "nine", "ninety", "nl", "no", "no-one", "nobody", "non", "none", "nonetheless", "noone", "nor", "normally", "nos", "not", "noted", "nothing", "notwithstanding", "novel", "now", "nowhere", "np", "nr", "nu", "null", "number", "numbers", "nz", "o", "obtain", "obtained", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "older", "oldest", "om", "omitted", "on", "once", "one", "one's", "ones", "only", "onto", "open", "opened", "opening", "opens", "opposite", "or", "ord", "order", "ordered", "ordering", "orders", "org", "other", "others", "otherwise", "ought", "oughtn't", "oughtnt", "our", "ours", "ourselves", "out", "outside", "over", "overall", "owing", "own", "p", "pa", "page", "pages", "part", "parted", "particular", "particularly", "parting", "parts", "past", "pe", "per", "perhaps", "pf", "pg", "ph", "pk", "pl", "place", "placed", "places", "please", "plus", "pm", "pmid", "pn", "point", "pointed", "pointing", "points", "poorly", "possible", "possibly", "potentially", "pp", "pr", "predominantly", "present", "presented", "presenting", "presents", "presumably", "previously", "primarily", "probably", "problem", "problems", "promptly", "proud", "provided", "provides", "pt", "put", "puts", "pw", "py", "q", "qa", "que", "quickly", "quite", "qv", "r", "ran", "rather", "rd", "re", "readily", "really", "reasonably", "recent", "recently", "ref", "refs", "regarding", "regardless", "regards", "related", "relatively", "research", "reserved", "respectively", "resulted", "resulting", "results", "right", "ring", "ro", "room", "rooms", "round", "ru", "run", "rw", "s", "sa", "said", "same", "saw", "say", "saying", "says", "sb", "sc", "sd", "se", "sec", "second", "secondly", "seconds", "section", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "sees", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "seventy", "several", "sg", "sh", "shall", "shan't", "shant", "she", "she'd", "she'll", "she's", "shed", "shell", "shes", "should", "should've", "shouldn", "shouldn't", "shouldnt", "show", "showed", "showing", "shown", "showns", "shows", "si", "side", "sides", "significant", "significantly", "similar", "similarly", "since", "sincere", "site", "six", "sixty", "sj", "sk", "sl", "slightly", "sm", "small", "smaller", "smallest", "sn", "so", "some", "somebody", "someday", "somehow", "someone", "somethan", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specifically", "specified", "specify", "specifying", "sr", "st", "state", "states", "still", "stop", "strongly", "su", "sub", "substantially", "successfully", "such", "sufficiently", "suggest", "sup", "sure", "sv", "sy", "system", "sz", "t", "t's", "take", "taken", "taking", "tc", "td", "tell", "ten", "tends", "test", "text", "tf", "tg", "th", "than", "thank", "thanks", "thanx", "that", "that'll", "that's", "that've", "thatll", "thats", "thatve", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "there'd", "there'll", "there're", "there's", "there've", "thereafter", "thereby", "thered", "therefore", "therein", "therell", "thereof", "therere", "theres", "thereto", "thereupon", "thereve", "these", "they", "they'd", "they'll", "they're", "they've", "theyd", "theyll", "theyre", "theyve", "thick", "thin", "thing", "things", "think", "thinks", "third", "thirty", "this", "thorough", "thoroughly", "those", "thou", "though", "thoughh", "thought", "thoughts", "thousand", "three", "throug", "through", "throughout", "thru", "thus", "til", "till", "tip", "tis", "tj", "tk", "tm", "tn", "to", "today", "together", "too", "took", "top", "toward", "towards", "tp", "tr", "tried", "tries", "trillion", "truly", "try", "trying", "ts", "tt", "turn", "turned", "turning", "turns", "tv", "tw", "twas", "twelve", "twenty", "twice", "two", "tz", "u", "ua", "ug", "uk", "um", "un", "under", "underneath", "undoing", "unfortunately", "unless", "unlike", "unlikely", "until", "unto", "up", "upon", "ups", "upwards", "us", "use", "used", "useful", "usefully", "usefulness", "uses", "using", "usually", "uucp", "uy", "uz", "v", "va", "value", "various", "vc", "ve", "versus", "very", "vg", "vi", "via", "viz", "vn", "vol", "vols", "vs", "vu", "w", "want", "wanted", "wanting", "wants", "was", "wasn", "wasn't", "wasnt", "way", "ways", "we", "we'd", "we'll", "we're", "we've", "web", "webpage", "website", "wed", "welcome", "well", "wells", "went", "were", "weren", "weren't", "werent", "weve", "wf", "what", "what'd", "what'll", "what's", "what've", "whatever", "whatll", "whats", "whatve", "when", "when'd", "when'll", "when's", "whence", "whenever", "where", "where'd", "where'll", "where's", "whereafter", "whereas", "whereby", "wherein", "wheres", "whereupon", "wherever", "whether", "which", "whichever", "while", "whilst", "whim", "whither", "who", "who'd", "who'll", "who's", "whod", "whoever", "whole", "wholl", "whom", "whomever", "whos", "whose", "why", "why'd", "why'll", "why's", "widely", "width", "will", "willing", "wish", "with", "within", "without", "won", "won't", "wonder", "wont", "words", "work", "worked", "working", "works", "world", "would", "would've", "wouldn", "wouldn't", "wouldnt", "ws", "www", "x", "y", "ye", "year", "years", "yes", "yet", "you", "you'd", "you'll", "you're", "you've", "youd", "youll", "young", "younger", "youngest", "your", "youre", "yours", "yourself", "yourselves", "youve", "yt", "yu", "z", "za", "zero", "zm", "zr"};

    private DocumentManager() {
    }

    /**
     * Class for a 2D vector
     */
    public static class Vec2 {
        /**
         * Original x coordinate
         */
        float originalX;
        /**
         * Original y coordinate
         */
        float originalY;
        /**
         * x coordinate after overlap avoidance
         */
        float x;
        /**
         * y coordinate after overlap avoidance
         */
        float y;
        /**
         * Bounding Box of the corresponding word
         */
        Rect boundingBox;

        /**
         * Contructor for Vec2
         * @param x x coordinate
         * @param y y coordinate
         */
        Vec2(float x, float y) {
            this.x = x;
            this.y = y;
            originalX = x;
            originalY = y;
        }
    }

    /**
     * Constructor for ellipse, its parameters are predefined
     */
    public class Ellipse {
        /**
         *  Origin x
         */
        int h = 1280;
        /**
         *  Origin y
         */
        int k = 720;
        /**
         *
         *  half of the width
         */
        int a = 1040;
        /**
         *
         * half of the height
         */
        int b = 500;

        /**
         * Empty constructor since its parameters are predefined
         */
        Ellipse() {
        }
    }

    /**
     * Maximum measured relevance of the most important word
     */
    private float maximumWordRelevance = -1;

    /**
     * Document Manager Instance
     * @return
     */
    public static DocumentManager getInstance() {
        if (instance == null) {
            instance = new DocumentManager();
        }
        return instance;
    }

    /**
     * Reset the Document Manager
     */
    public void clear() {
        documentList.clear();
        documentVectors.clear();
        wordList.clear();
        maximumWordRelevance = -1;
    }

    /**
     * Adds a new document, and a new word to the named document
     * @param document Document name
     * @param term word in the socument
     */
    private void add(String document, String term) {
        if (documentList.containsKey(document)) {
            documentList.put(document, documentList.get(document) + 1);
        } else {
            documentList.put(document, 1);
        }


        Word w = new Word(term);
        w.add(document);
        if (wordList.contains(w)) {
            wordList.get(wordList.indexOf(w)).add(document);
        } else {
            wordList.add(w);
        }
    }

    /**
     * Calculates the normalized term frequency
     */
    private void calculateTermFrequency() {
        for (Word word : wordList) {
            HashMap<String, Float> termFrequency = new HashMap<>();
            HashMap<String, Integer> wordCount = word.getWordCount();
            for (String document : documentList.keySet()) {
                Integer count = wordCount.get(document);
                if (count == null) {
                    count = 0;
                }
                float tf = count / ((float) documentList.get(document));
                termFrequency.put(document, tf);
            }
            word.setTermFrequency(termFrequency);
        }
    }

    /**
     * Calculates the inverse document frequency
     * @param idfMode
     * idfMode = 0 tf*idf
     * idfMode = 1 idf = 1 - idf
     * idfMode = 2 if (idf == 0) > idf = 1
     */
    private void calculateInverseDocumentFrequency(int idfMode) {
        for (Word word : wordList) {
            HashMap<String, Integer> wordCount = word.getWordCount();
            float idf = (float) Math.log(((double) documentList.size()) / ((double) wordCount.size()));
            if (idfMode == 1) {
                idf = Math.abs(1-idf);
            } else if (idfMode == 2) {
                if (idf == 0) {
                    idf = 1;
                }
            }
            word.setInverseDocumentFrequency(idf);
            System.out.println("Inverse Document Frequency for " + word.getTerm() + " is: " + word.getInverseDocumentFrequency());
        }
    }

    /**
     * Calculates all values for Word classification
     * determines original position
     * and resolves overlaps
     */
    public void process(int idfMode) {
        long time = System.currentTimeMillis();
        calculateDocumentVectors();
        long time2 = System.currentTimeMillis();
        Log.d("Performance", "document vectors took " + ((time2 - time)) + " ms");
        time = System.currentTimeMillis();


        Log.d("Word Filter Performance", "word count before " + wordList.size());
        selectTopWordsPerDocument();
        Log.d("Word Filter Performance", "word count after " + wordList.size());
        time2 = System.currentTimeMillis();
        Log.d("Performance", "selecting top 100 per category took " + ((time2 - time)) + " ms");
        time = System.currentTimeMillis();

        calculateTermFrequency();
        time2 = System.currentTimeMillis();
        Log.d("Performance", "calculate tf took " + ((time2 - time)) + " ms");
        time = System.currentTimeMillis();

        calculateInverseDocumentFrequency(idfMode);
        time2 = System.currentTimeMillis();
        Log.d("Performance", "calculate idf took " + ((time2 - time)) + " ms");
        time = System.currentTimeMillis();
        calculatePosition();
        time2 = System.currentTimeMillis();
        Log.d("Performance", "calc pos took " + ((time2 - time)) + " ms");
        time = System.currentTimeMillis();

        resolveOverlaps();
        time2 = System.currentTimeMillis();
        Log.d("Performance", "resolve overlaps took " + ((time2 - time)) + " ms");

    }

    /**
     * Selects the top words in each document.
     * If a word appears in several documents, it is only added once reducing the total number.
     */
    private void selectTopWordsPerDocument() {
        int numberOfWords = 50;
        if (documentList.size() == 4) {
            numberOfWords = 30;
        } else if (documentList.size() == 5) {
            numberOfWords = 25;
        } else if (documentList.size() == 6) {
            numberOfWords = 20;
        }
        Map<String, LinkedList<Word>> selectedWords = new HashMap<>();
        for (String document : documentList.keySet()) {
            selectedWords.put(document, new LinkedList<Word>());
        }
        Log.d("Word Filter Performance", "before selection");
        long time = System.currentTimeMillis();
        for (Word word : wordList) {
            for (String document : word.getWordCount().keySet()) {
                Integer countInDocument = word.getWordCount().get(document);
                if (countInDocument != null && countInDocument > 0) {
                    LinkedList<Word> list = selectedWords.get(document);
                    for (int i = 0; i < numberOfWords; i++) {
                        if (list.size() == i || list.get(i).getWordCount().get(document) < word.getWordCount().get(document)) {
                            list.add(i, word);
                            break;
                        }
                    }
                }
            }
        }
        Log.d("Word Filter Performance", "selection took " + (System.currentTimeMillis() - time) + " ms");
        time = System.currentTimeMillis();
        wordList.clear();
        for (String document : selectedWords.keySet()) {
            int i = 0;
            while (i < 100 && i < selectedWords.get(document).size()) {
                Word selectedWord = selectedWords.get(document).get(i++);
                if (selectedWord != null && !wordList.contains(selectedWord)) {
                    wordList.add(selectedWord);
                }
            }
        }
        Log.d("Word Filter Performance", "filtering took " + (System.currentTimeMillis() - time) + " ms");
    }

    /**
     * Calculate the bounding boxes of all words
     */
    private void calculateBoundingBoxes() {
        //calculate bounding boxes
        Paint paint = new Paint();
        for (Word word : wordList) {
            word.calculateBoundingBox(paint, maximumWordRelevance);
        }
    }

    /**
     * Calculates the directions of the documents
     */
    private void calculateDocumentVectors() {
        float categorySize = 360 / documentList.size();
        float angle = categorySize / 2;
        for (int i = 0; i < documentList.size(); i++) {
            float currentAngle = categorySize * i + angle;
            String document = documentList.keySet().toArray(new String[0])[i];
            float x = (float) Math.cos(Math.toRadians(currentAngle));
            float y = (float) Math.sqrt(1 - x * x);


            if (categorySize * i + angle > 180) {
                y = -y;
            }
            Log.d("docdirection", document + " " + currentAngle + " " + x + ", " + y);

            documentVectors.put(document, new Vec2(x, y));
        }
    }

    /**
     * reads document
     * thei function is called if no document chosen
     * @param ctx android context
     * @param resourceId id of document
     * @param documentName name of the documents
     */
    public void readDocument(Context ctx, int resourceId, String documentName) {
        processInputFile(ctx.getResources().openRawResource(resourceId), documentName);
    }

    /**
     * reads document
     * @param path path to the document
     * @param documentName tname of the document
     * @throws FileNotFoundException
     */
    public void readDocument(String path, String documentName) throws FileNotFoundException {
        processInputFile(new FileInputStream(path), documentName);
    }

    /**
     * reads the input
     * @param inputStream
     * @param documentName
     */
    private void processInputFile(InputStream inputStream, String documentName) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String x = "";
            x = r.readLine();


            while (x != null) {

//                Log.d("test", x);
                x = x.replaceAll("Lin ", "lin_");
                x = x.replaceAll("Yuan Dan", "yuan_dan");
                x = x.replaceAll("Heavenly ", "hevenly_");
                x = x.replaceAll("Earthly ", "earthly_");
                x = x.replaceAll("Dong-er", "lin_dong");
                x = x.replaceAll("Liu Yan", "liu_yan");
//                Log.d("test", x);
                String[] split = x.split(" ");

//
                if (!"".equals(x)) {
                    for (String word : split) {
                        word = word.toLowerCase();
                        word = word.replace(":", "");
                        word = word.replace(";", "");
                        word = word.replace("`s", "");
                        word = word.replace("’s", "");
                        word = word.replace("'s", "");
                        word = word.replace("’", "");
                        word = word.replace("`", "");
                        word = word.replace("'", "");
                        word = word.replace(",", "");
                        word = word.replace("“", "");
                        word = word.replace("”", "");
                        word = word.replace(".", "");
                        word = word.replace("\"", "");
                        word = word.replace("!", "");
                        word = word.replace("?", "");
                        word = word.replace("…", " ");
                        word = word.replace("\uFEFF", "");

                        boolean ok = true;
                        for (String stopword : stopwords) {
                            if (word.equals(stopword.toLowerCase())) {
                                ok = false;
                            }
                        }

                        if (ok) {
                            if("the".equals(word)){
                                Log.d("Word Filter", "failed to filter 'the'");
                            }

                            add(documentName, word);
                        }
                    }
                }
                x = r.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the original position of the words
     */
    private void calculatePosition() {
        /* The font size of each word encodes the maximum relevance
        value for the word in all categories*/
        for (Word word : wordList) {
            float maximumCategoryWeight = -1;
            HashMap<String, Float> categoryWeights = new HashMap<>();
            HashMap<String, Float> termFrequency = word.getTermFrequency();
            for (String document : termFrequency.keySet()) {
                float weight = termFrequency.get(document) * word.getInverseDocumentFrequency();
                categoryWeights.put(document, weight);
                if (weight > maximumCategoryWeight) {
                    maximumCategoryWeight = weight;
                }
                if (weight > maximumWordRelevance) {
                    maximumWordRelevance = weight;
                }
            }
            word.setCategoryWeights(categoryWeights);
            word.setMaximumRelevance(maximumCategoryWeight);
        }
        for (Word currentWord : wordList) {
            HashMap<String, Float> normalizedWeights = new HashMap<>();
            for (String document : documentList.keySet()) {
                float otherWordWeights = 0;
                for (Word otherWord : wordList) {
                    otherWordWeights += otherWord.getCategoryWeights().get(document);
                }
                normalizedWeights.put(document, currentWord.getCategoryWeights().get(document) / otherWordWeights);
            }
            currentWord.setNormalizedWeights(normalizedWeights);
        }
        for (Word word : wordList) {
            HashMap<String, Float> normalizedWeights = word.getNormalizedWeights();
            HashMap<String, Float> positionWeights = new HashMap<>();
            float sumNormalizedWeights = 0;
            for (String document : normalizedWeights.keySet()) {
                sumNormalizedWeights += normalizedWeights.get(document);
            }
            for (String document : normalizedWeights.keySet()) {
                positionWeights.put(document, (normalizedWeights.get(document) / sumNormalizedWeights));
            }
            word.setPlacementWeights(positionWeights);
        }
        for (Word word : wordList) {
            Vec2 intendedPos = new Vec2(0, 0);
            HashMap<String, Float> positionWeights = word.getPlacementWeights();
            for (String document : positionWeights.keySet()) {
                intendedPos.x += documentVectors.get(document).x * positionWeights.get(document);
                intendedPos.y += documentVectors.get(document).y * positionWeights.get(document);
            }
            intendedPos.x = 1280 + 640 * intendedPos.x;
            intendedPos.y = 770 + 385 * intendedPos.y;
            word.setIntendedPosition(intendedPos);
        }
    }

    /**
     * getter for all documents
     * @return all documents
     */
    public HashMap<String, Integer> getDocumentList() {
        return documentList;
    }

    /**
     * getter for all words
     * @return all words
     */
    public ArrayList<Word> getWordList() {
        return wordList;
    }

    /**
     * resolves overlaps based on paper:
     * "Rolled-out Wordles: A heuristic Method for Overlap Removal of 2D Data Representatives." Strobelt, Hendrik, et al.
     */
    public void resolveOverlaps() {
        LinkedList<Word> circleSorted = new LinkedList<>(); //sorted by distance to center

        for (Word word : wordList) {
            if (circleSorted.size() == 0) {
                circleSorted.add(word);
            } else {
                for (int i = 0; i < circleSorted.size(); i++) {
                    Word sortedWord = circleSorted.get(i);
//                    if (Math.abs(1280 - sortedWord.getPosition().x) + Math.abs(770 - sortedWord.getPosition().y) > Math.abs(1280 - word.getPosition().x) + Math.abs(770 - word.getPosition().y)) {
                    if (word.getMaximumRelevance() > sortedWord.getMaximumRelevance()) {
                        circleSorted.add(i, word);
                        break;
                    }
                }
                if (!circleSorted.contains(word)) {
                    circleSorted.add(word);
                }
            }
        }

        calculateBoundingBoxes();
        List<Word> placedWords = new ArrayList<>();
        for (Word word : circleSorted) {
            word.getPosition().originalX = word.getPosition().x;
            word.getPosition().originalY = word.getPosition().y;
            int i = 0;
            while (i < 500 && checkForOverlap(placedWords, word)) {
                fixOverlaps(word, i++);
                word.calculateBoundingBox(maximumWordRelevance);
            }
            placedWords.add(word);
        }
    }

    /**
     * Determine new position wor word to avoid overlap
     * @param word word to find a new position
     * @param i ith iteration
     */
    private void fixOverlaps(Word word, int i) {
        Vec2 offset = SpiralGenerator.calculateSpiral(i);
        word.getPosition().x = word.getPosition().originalX + offset.x;
        word.getPosition().y = word.getPosition().originalY + offset.y;
//        Log.d("Rad fixOverlap", word.getTerm() + " " + word.getPosition().x + " " + word.getPosition().y);
    }

    /**
     * Check if there is an overlap
     * @param placedWords words with fixed position
     * @param word word with new position
     * @return
     */
    private boolean checkForOverlap(List<Word> placedWords, Word word) {
        if (outsideTheRing(word)) { //check ellipse
            return true;
        }
        for (Word placedWord : placedWords) {
            if (overlaps(word.getPosition().boundingBox, placedWord.getPosition().boundingBox)
                    || overlaps(placedWord.getPosition().boundingBox, word.getPosition().boundingBox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * getter for maximum word relevance
     * @return maximum word relevance
     */
    float getMaximumWordRelevance() {
        return maximumWordRelevance;
    }

    /**
     * Check if word is outside the ellipse
     * @param word word to check
     * @return
     */
    private boolean outsideTheRing(Word word) {
        //Equation of ellipse: (x-h)^2/a^2 + (y-k)^2/b^2 = 1 (h,k) center a, b horizontal/vertical radius
        Rect bb = word.getPosition().boundingBox;
        if (bb.right == 0) {
            Log.d("Ring Check", "skipped due to 0 for word " + word.getTerm());
            return false;
        }
        boolean result = false;
        if (((Math.pow((bb.left - frame.h), 2) / Math.pow(frame.a, 2)) + ((Math.pow((bb.bottom - frame.k), 2) / Math.pow(frame.b, 2)))) >= 1) {
            result = true;
        } else if (((Math.pow((bb.left - frame.h), 2) / Math.pow(frame.a, 2)) + ((Math.pow((bb.top - frame.k), 2) / Math.pow(frame.b, 2)))) >= 1) {
            result = true;
        } else if (((Math.pow((bb.right - frame.h), 2) / Math.pow(frame.a, 2)) + ((Math.pow((bb.bottom - frame.k), 2) / Math.pow(frame.b, 2)))) >= 1) {
            result = true;
        } else if (((Math.pow((bb.right - frame.h), 2) / Math.pow(frame.a, 2)) + ((Math.pow((bb.top - frame.k), 2) / Math.pow(frame.b, 2)))) >= 1) {
            result = true;
        }
        return result;
    }

    /**
     * chekc if the two rectangles overlap
     * @param bb1
     * @param bb2
     * @return
     */
    private boolean overlaps(Rect bb1, Rect bb2) {
        ArrayList<Vec2> points = new ArrayList<>();
        points.add(new Vec2(bb2.left, bb2.bottom));
        points.add(new Vec2(bb2.right, bb2.top));
        points.add(new Vec2(bb2.right, bb2.bottom));
        points.add(new Vec2(bb2.centerX(), bb2.centerY()));

        for (Vec2 point : points) {
            if (bb1.left <= point.x && point.x <= bb1.right) {
                if (bb1.bottom <= point.y && point.y <= bb1.top) {
                    return true;
                }
            }
        }
        return false;

    }
}

