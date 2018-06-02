package io.github.syzygy2048.radcloud;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DocumentManager {

    private HashMap<String, Integer> documentList = new HashMap<>();
    private HashMap<String, Vec2> documentVectors = new HashMap<>();
    private ArrayList<Word> wordList = new ArrayList<>();

    private static DocumentManager instance;


    private String[] stopwords = new String[]{"'ll", "'tis", "'twas", "'ve", "10", "39", "a", "a's", "able", "ableabout", "about", "above", "abroad", "abst", "accordance", "according", "accordingly", "across", "act", "actually", "ad", "added", "adj", "adopted", "ae", "af", "affected", "affecting", "affects", "after", "afterwards", "ag", "again", "against", "ago", "ah", "ahead", "ai", "ain't", "aint", "al", "all", "allow", "allows", "almost", "alone", "along", "alongside", "already", "also", "although", "always", "am", "amid", "amidst", "among", "amongst", "amoungst", "amount", "an", "and", "announce", "another", "any", "anybody", "anyhow", "anymore", "anyone", "anything", "anyway", "anyways", "anywhere", "ao", "apart", "apparently", "appear", "appreciate", "appropriate", "approximately", "aq", "ar", "are", "area", "areas", "aren", "aren't", "arent", "arise", "around", "arpa", "as", "aside", "ask", "asked", "asking", "asks", "associated", "at", "au", "auth", "available", "aw", "away", "awfully", "az", "b", "ba", "back", "backed", "backing", "backs", "backward", "backwards", "bb", "bd", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "began", "begin", "beginning", "beginnings", "begins", "behind", "being", "beings", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "bf", "bg", "bh", "bi", "big", "bill", "billion", "biol", "bj", "bm", "bn", "bo", "both", "bottom", "br", "brief", "briefly", "bs", "bt", "but", "buy", "bv", "bw", "by", "bz", "c", "c'mon", "c's", "ca", "call", "came", "can", "can't", "cannot", "cant", "caption", "case", "cases", "cause", "causes", "cc", "cd", "certain", "certainly", "cf", "cg", "ch", "changes", "ci", "ck", "cl", "clear", "clearly", "click", "cm", "cmon", "cn", "co", "co.", "com", "come", "comes", "computer", "con", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "copy", "corresponding", "could", "could've", "couldn", "couldn't", "couldnt", "course", "cr", "cry", "cs", "cu", "currently", "cv", "cx", "cy", "cz", "d", "dare", "daren't", "darent", "date", "de", "dear", "definitely", "describe", "described", "despite", "detail", "did", "didn", "didn't", "didnt", "differ", "different", "differently", "directly", "dj", "dk", "dm", "do", "does", "doesn", "doesn't", "doesnt", "doing", "don", "don't", "done", "dont", "doubtful", "down", "downed", "downing", "downs", "downwards", "due", "during", "dz", "e", "each", "early", "ec", "ed", "edu", "ee", "effect", "eg", "eh", "eight", "eighty", "either", "eleven", "else", "elsewhere", "empty", "end", "ended", "ending", "ends", "enough", "entirely", "er", "es", "especially", "et", "et-al", "etc", "even", "evenly", "ever", "evermore", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "f", "face", "faces", "fact", "facts", "fairly", "far", "farther", "felt", "few", "fewer", "ff", "fi", "fifteen", "fifth", "fifty", "fify", "fill", "find", "finds", "fire", "first", "five", "fix", "fj", "fk", "fm", "fo", "followed", "following", "follows", "for", "forever", "former", "formerly", "forth", "forty", "forward", "found", "four", "fr", "free", "from", "front", "full", "fully", "further", "furthered", "furthering", "furthermore", "furthers", "fx", "g", "ga", "gave", "gb", "gd", "ge", "general", "generally", "get", "gets", "getting", "gf", "gg", "gh", "gi", "give", "given", "gives", "giving", "gl", "gm", "gmt", "gn", "go", "goes", "going", "gone", "good", "goods", "got", "gotten", "gov", "gp", "gq", "gr", "great", "greater", "greatest", "greetings", "group", "grouped", "grouping", "groups", "gs", "gt", "gu", "gw", "gy", "h", "had", "hadn't", "hadnt", "half", "happens", "hardly", "has", "hasn", "hasn't", "hasnt", "have", "haven", "haven't", "havent", "having", "he", "he'd", "he'll", "he's", "hed", "hell", "hello", "help", "hence", "her", "here", "here's", "hereafter", "hereby", "herein", "heres", "hereupon", "hers", "herself", "herse”", "hes", "hi", "hid", "high", "higher", "highest", "him", "himself", "himse”", "his", "hither", "hk", "hm", "hn", "home", "homepage", "hopefully", "how", "how'd", "how'll", "how's", "howbeit", "however", "hr", "ht", "htm", "html", "http", "hu", "hundred", "i", "i'd", "i'll", "i'm", "i've", "i.e.", "id", "ie", "if", "ignored", "ii", "il", "ill", "im", "immediate", "immediately", "importance", "important", "in", "inasmuch", "inc", "inc.", "indeed", "index", "indicate", "indicated", "indicates", "information", "inner", "inside", "insofar", "instead", "int", "interest", "interested", "interesting", "interests", "into", "invention", "inward", "io", "iq", "ir", "is", "isn", "isn't", "isnt", "it", "it'd", "it'll", "it's", "itd", "itll", "its", "itself", "itse”", "ive", "j", "je", "jm", "jo", "join", "jp", "just", "k", "ke", "keep", "keeps", "kept", "keys", "kg", "kh", "ki", "kind", "km", "kn", "knew", "know", "known", "knows", "kp", "kr", "kw", "ky", "kz", "l", "la", "large", "largely", "last", "lately", "later", "latest", "latter", "latterly", "lb", "lc", "least", "length", "less", "lest", "let", "let's", "lets", "li", "like", "liked", "likely", "likewise", "line", "little", "lk", "ll", "long", "longer", "longest", "look", "looking", "looks", "low", "lower", "lr", "ls", "lt", "ltd", "lu", "lv", "ly", "m", "ma", "made", "mainly", "make", "makes", "making", "man", "many", "may", "maybe", "mayn't", "maynt", "mc", "md", "me", "mean", "means", "meantime", "meanwhile", "member", "members", "men", "merely", "mg", "mh", "microsoft", "might", "might've", "mightn't", "mightnt", "mil", "mill", "million", "mine", "minus", "miss", "mk", "ml", "mm", "mn", "mo", "more", "moreover", "most", "mostly", "move", "mp", "mq", "mr", "mrs", "ms", "msie", "mt", "mu", "much", "mug", "must", "must've", "mustn't", "mustnt", "mv", "mw", "mx", "my", "myself", "myse”", "mz", "n", "na", "name", "namely", "nay", "nc", "nd", "ne", "near", "nearly", "necessarily", "necessary", "need", "needed", "needing", "needn't", "neednt", "needs", "neither", "net", "netscape", "never", "neverf", "neverless", "nevertheless", "new", "newer", "newest", "next", "nf", "ng", "ni", "nine", "ninety", "nl", "no", "no-one", "nobody", "non", "none", "nonetheless", "noone", "nor", "normally", "nos", "not", "noted", "nothing", "notwithstanding", "novel", "now", "nowhere", "np", "nr", "nu", "null", "number", "numbers", "nz", "o", "obtain", "obtained", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "older", "oldest", "om", "omitted", "on", "once", "one", "one's", "ones", "only", "onto", "open", "opened", "opening", "opens", "opposite", "or", "ord", "order", "ordered", "ordering", "orders", "org", "other", "others", "otherwise", "ought", "oughtn't", "oughtnt", "our", "ours", "ourselves", "out", "outside", "over", "overall", "owing", "own", "p", "pa", "page", "pages", "part", "parted", "particular", "particularly", "parting", "parts", "past", "pe", "per", "perhaps", "pf", "pg", "ph", "pk", "pl", "place", "placed", "places", "please", "plus", "pm", "pmid", "pn", "point", "pointed", "pointing", "points", "poorly", "possible", "possibly", "potentially", "pp", "pr", "predominantly", "present", "presented", "presenting", "presents", "presumably", "previously", "primarily", "probably", "problem", "problems", "promptly", "proud", "provided", "provides", "pt", "put", "puts", "pw", "py", "q", "qa", "que", "quickly", "quite", "qv", "r", "ran", "rather", "rd", "re", "readily", "really", "reasonably", "recent", "recently", "ref", "refs", "regarding", "regardless", "regards", "related", "relatively", "research", "reserved", "respectively", "resulted", "resulting", "results", "right", "ring", "ro", "room", "rooms", "round", "ru", "run", "rw", "s", "sa", "said", "same", "saw", "say", "saying", "says", "sb", "sc", "sd", "se", "sec", "second", "secondly", "seconds", "section", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "sees", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "seventy", "several", "sg", "sh", "shall", "shan't", "shant", "she", "she'd", "she'll", "she's", "shed", "shell", "shes", "should", "should've", "shouldn", "shouldn't", "shouldnt", "show", "showed", "showing", "shown", "showns", "shows", "si", "side", "sides", "significant", "significantly", "similar", "similarly", "since", "sincere", "site", "six", "sixty", "sj", "sk", "sl", "slightly", "sm", "small", "smaller", "smallest", "sn", "so", "some", "somebody", "someday", "somehow", "someone", "somethan", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specifically", "specified", "specify", "specifying", "sr", "st", "state", "states", "still", "stop", "strongly", "su", "sub", "substantially", "successfully", "such", "sufficiently", "suggest", "sup", "sure", "sv", "sy", "system", "sz", "t", "t's", "take", "taken", "taking", "tc", "td", "tell", "ten", "tends", "test", "text", "tf", "tg", "th", "than", "thank", "thanks", "thanx", "that", "that'll", "that's", "that've", "thatll", "thats", "thatve", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "there'd", "there'll", "there're", "there's", "there've", "thereafter", "thereby", "thered", "therefore", "therein", "therell", "thereof", "therere", "theres", "thereto", "thereupon", "thereve", "these", "they", "they'd", "they'll", "they're", "they've", "theyd", "theyll", "theyre", "theyve", "thick", "thin", "thing", "things", "think", "thinks", "third", "thirty", "this", "thorough", "thoroughly", "those", "thou", "though", "thoughh", "thought", "thoughts", "thousand", "three", "throug", "through", "throughout", "thru", "thus", "til", "till", "tip", "tis", "tj", "tk", "tm", "tn", "to", "today", "together", "too", "took", "top", "toward", "towards", "tp", "tr", "tried", "tries", "trillion", "truly", "try", "trying", "ts", "tt", "turn", "turned", "turning", "turns", "tv", "tw", "twas", "twelve", "twenty", "twice", "two", "tz", "u", "ua", "ug", "uk", "um", "un", "under", "underneath", "undoing", "unfortunately", "unless", "unlike", "unlikely", "until", "unto", "up", "upon", "ups", "upwards", "us", "use", "used", "useful", "usefully", "usefulness", "uses", "using", "usually", "uucp", "uy", "uz", "v", "va", "value", "various", "vc", "ve", "versus", "very", "vg", "vi", "via", "viz", "vn", "vol", "vols", "vs", "vu", "w", "want", "wanted", "wanting", "wants", "was", "wasn", "wasn't", "wasnt", "way", "ways", "we", "we'd", "we'll", "we're", "we've", "web", "webpage", "website", "wed", "welcome", "well", "wells", "went", "were", "weren", "weren't", "werent", "weve", "wf", "what", "what'd", "what'll", "what's", "what've", "whatever", "whatll", "whats", "whatve", "when", "when'd", "when'll", "when's", "whence", "whenever", "where", "where'd", "where'll", "where's", "whereafter", "whereas", "whereby", "wherein", "wheres", "whereupon", "wherever", "whether", "which", "whichever", "while", "whilst", "whim", "whither", "who", "who'd", "who'll", "who's", "whod", "whoever", "whole", "wholl", "whom", "whomever", "whos", "whose", "why", "why'd", "why'll", "why's", "widely", "width", "will", "willing", "wish", "with", "within", "without", "won", "won't", "wonder", "wont", "words", "work", "worked", "working", "works", "world", "would", "would've", "wouldn", "wouldn't", "wouldnt", "ws", "www", "x", "y", "ye", "year", "years", "yes", "yet", "you", "you'd", "you'll", "you're", "you've", "youd", "youll", "young", "younger", "youngest", "your", "youre", "yours", "yourself", "yourselves", "youve", "yt", "yu", "z", "za", "zero", "zm", "zr"};

    private DocumentManager() {
    }

    public class Vec2 {
        float originalX;
        float originalY;
        float x;
        float y;
        float entropy = 0;

        Rect boundingBox;

        Vec2(float x, float y) {
            this.x = x;
            this.y = y;
            originalX = x;
            originalY = y;
        }
    }

    private float maximumWordRelevance = -1;

    public static DocumentManager getInstance() {
        if (instance == null) {
            instance = new DocumentManager();
        }
        return instance;
    }


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

    private void calculateInverseDocumentFrequency() {
        for (Word word : wordList) {
            HashMap<String, Integer> wordCount = word.getWordCount();
            word.setInverseDocumentFrequency((float) Math.log(((double) documentList.size()) / ((double) wordCount.size())));
            System.out.println("Inverse Document Frequency for " + word.getTerm() + " is: " + word.getInverseDocumentFrequency());
        }
    }

    public void process() {
        calculateDocumentVectors();
//        selectTop100WordsPerDocument();
        calculateTermFrequency();
        calculateInverseDocumentFrequency();
        calculatePosition();
        resolveOverlaps();
    }

    /**
     * Selects the top 100 words in each document.
     * If a word appears in several documents, it is only added once reducing the total number. (e.g
     * TODO: make sure this doesn't fuck up the other calculations
     */
    private void selectTop100WordsPerDocument() {
        Map<String, LinkedList<Word>> selectedWords = new HashMap<>();
        for (String document : documentList.keySet()) {
            selectedWords.put(document, new LinkedList<Word>());
        }
        for (Word word : wordList) {
            for (String document : word.getWordCount().keySet()) {
                Integer countInDocument = word.getWordCount().get(document);
                if (countInDocument != null && countInDocument > 0) {
                    LinkedList<Word> list = selectedWords.get(document);
                    for (int i = 0; i < 100; i++) {
                        if (list.size() == i || list.get(i).getWordCount().get(document) < word.getWordCount().get(document)) {
                            list.add(i, word);
                            break;
                        }
                    }
                }
            }
        }
        wordList.clear();
        for (String document : selectedWords.keySet()) {
            int i = 0;
            while (i++ < 100 && i < selectedWords.get(document).size()) {
                Word selectedWord = selectedWords.get(document).get(i);
                if (selectedWord != null && !wordList.contains(selectedWord)) {
                    wordList.add(selectedWord);
                }
            }
        }
        Log.d("RadCloud Filter", "number of words " + wordList.size());
    }

    private void calculateBoundingBoxes() {
        //calculate bounding boxes
        Paint paint = new Paint();
        float textWidth;
        for (Word word : wordList) {
            float textSize = RadCloudActivity.MAXIMUM_TEXT_SIZE * (word.getMaximumRelevance() / maximumWordRelevance);
            if (textSize < 5.0) {
                textSize = 5;
            }
            paint.setTextSize(textSize);
            textWidth = paint.measureText(word.getTerm());

            Rect rect = new Rect(
                    (int) (word.getPosition().x - textWidth / 2),
                    (int) (word.getPosition().y + textSize / 2),
                    (int) (word.getPosition().x + textWidth / 2),
                    (int) (word.getPosition().y - textSize / 2));
            word.getPosition().boundingBox = rect;
//            canvas.drawRect(rect, new Paint());
        }
    }

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


    public void readDocument(Context ctx, int resourceId, String documentName) {
        InputStream inputStream = ctx.getResources().openRawResource(resourceId);


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

                        boolean ok = true;
                        for (String stopword : stopwords) {
                            if (word.equals(stopword.toLowerCase())) {
                                ok = false;
                            }
                        }

                        if (ok) {
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


//        for (Word word : wordList) {
//            HashMap<String, Float> normalizedWeights = new HashMap<>();
//            HashMap<String, Float> categoryWeights = new HashMap<>();
//            HashMap<String, Float> termFrequency = word.getTermFrequency();
//            for (String document : termFrequency.keySet()) {
//                categoryWeights.put(document, (termFrequency.get(document) *  word.getInverseDocumentFrequency()) / documentList.get(document));
//            }
//
//            HashMap<String, Float> documentWeights = new HashMap<>();
//
//            float wordTotalWeight = 0;
//            for (String normalizedWeight : normalizedWeights.keySet()) {
//                wordTotalWeight += normalizedWeights.get(normalizedWeight);
//            }
//            for (String document : termFrequency.keySet()) {
//                documentWeights.put(document, normalizedWeights.get(document) / wordTotalWeight);
//            }
//
//            word.setNormalizedWeights(normalizedWeights);
//            word.setDocumentWeights(documentWeights);
//
//            Vec2 intendedPos = new Vec2(0,0);
//            for (String document : documentWeights.keySet()) {
//                Vec2 documentVector = documentVectors.get(document);
//                float weight = documentWeights.get(document);
//                intendedPos.x += weight * documentVector.x;
//                intendedPos.y += weight * documentVector.y;
//            }
//            Log.d("bla", word.getTerm() + " " + intendedPos.x + ", " + intendedPos.y);
//            word.setIntendedPosition(intendedPos);
//        }
    }

    public HashMap<String, Integer> getDocumentList() {
        return documentList;
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }


    public void resolveOverlaps() {
        LinkedList<Word> circleSorted = new LinkedList<>(); //sorted by distance to center

        for (Word word : wordList) {
            if (circleSorted.size() == 0) {
                circleSorted.add(word);
            } else {
                for (int i = 0; i < circleSorted.size(); i++) {
                    Word sortedWord = circleSorted.get(i);
                    if (Math.abs(1280 - sortedWord.getPosition().x) + Math.abs(770 - sortedWord.getPosition().y) > Math.abs(1280 - word.getPosition().x) + Math.abs(770 - word.getPosition().y)) {
                        circleSorted.add(i, word);
                        break;
                    }
                }
                if (!circleSorted.contains(word)) {
                    circleSorted.add(word);
                }
            }
        }
        for (Word word : circleSorted) {
            Log.d("Rad Csort Length", word.getTerm() + " " + (Math.abs(1280 - word.getPosition().x) + Math.abs(770 - word.getPosition().y)));
        }

        calculateBoundingBoxes();
        List<Word> placedWords = new ArrayList<>();
        for (Word word : circleSorted) {
            int i = 0;
            word.getPosition().originalX = word.getPosition().x;
            word.getPosition().originalY = word.getPosition().y;
            while(checkForOverlap(placedWords, word)){
                fixOverlaps(word, i++);
                calculateBoundingBoxes();
            }
            placedWords.add(word);
        }
    }

    private void fixOverlaps(Word word, int i){
        ArrayList<Vec2> positionList = new ArrayList<>();
        positionList.add(new Vec2(10, 0));
        positionList.add(new Vec2(10, 5));
        positionList.add(new Vec2(10, 10));
        positionList.add(new Vec2(5, 10));
        positionList.add(new Vec2(0, 10));
        positionList.add(new Vec2(-5, 10));
        positionList.add(new Vec2(-10, 10));
        positionList.add(new Vec2(-10, 5));
        positionList.add(new Vec2(-10, 0));
        positionList.add(new Vec2(-10, -5));
        positionList.add(new Vec2(-10, -10));
        positionList.add(new Vec2(-5, -10));
        positionList.add(new Vec2(0, -10));
        positionList.add(new Vec2(5, -10));
        positionList.add(new Vec2(10, -10));
        positionList.add(new Vec2(10, -5));

        word.getPosition().x = word.getPosition().originalX + positionList.get(i%positionList.size()).x * (int)(Math.floor(i/positionList.size()) + 1);
        word.getPosition().y = word.getPosition().originalY + positionList.get(i%positionList.size()).y * (int)(Math.floor(i/positionList.size()) + 1);
//        Log.d("Rad fixOverlap", word.getTerm() + " " + word.getPosition().x + " " + word.getPosition().y);
    }

    private boolean checkForOverlap(List<Word> placedWords, Word word) {
        for (Word placedWord : placedWords) {
            if (overlaps(word.getPosition().boundingBox, placedWord.getPosition().boundingBox)) {
                return true;
            }
        }
        return false;
    }
//        if (list.size() == i || list.get(i).getWordCount().get(document) < word.getWordCount().get(document)) {
//            list.add(i, word);
//            break;
//        }
//    }

//        }
//
//
//
//
//        int attempts = 200; //prevent endless loop if cluster resolving fails
//        calculateBoundingBoxes();
//        calculateOverlapClusters(listOfClusters);
//        while (wordList.size() != listOfClusters.size() && attempts-- > 0) {
//            Log.d("Radcloud Overlap", "attempts " + attempts + " " + wordList.size() + " " + listOfClusters.size());
//            resolveClusters(listOfClusters);
//            calculateBoundingBoxes();
//            listOfClusters = new ArrayList<>();
//            calculateOverlapClusters(listOfClusters);
//        }
//}

    float getMaximumWordRelevance() {
        return maximumWordRelevance;
    }


    private void circleResolve() {

    }

    private void resolveClusters(List<List<Word>> listOfClusters) {
        Random rnd = new Random();
        for (List<Word> cluster : listOfClusters) {
            if (cluster.size() > 1) {
                float centerX = 0;
                float centerY = 0;
                for (Word word : cluster) {
                    centerX += word.getPosition().boundingBox.centerX();
                    centerY += word.getPosition().boundingBox.centerY();
                }
                centerX /= cluster.size();
                centerY /= cluster.size();
                float xOffset;
                float yOffset;
                for (Word word : cluster) {
                    xOffset = centerX - word.getPosition().boundingBox.centerX();
                    yOffset = centerY - word.getPosition().boundingBox.centerY();
                    if (xOffset == 0 && yOffset == 0) {
                        xOffset = ((rnd.nextFloat() % 2 - 1) * 0.00005f);
                        yOffset = ((rnd.nextFloat() % 2 - 1) * 0.00005f);
                    }
                    word.getPosition().x += xOffset;
                    word.getPosition().y += yOffset;
                }
            }
        }
    }

    private void calculateOverlapClusters(List<List<Word>> listOfClusters) {
        for (Word word : DocumentManager.getInstance().getWordList()) {
            boolean overlap = false;
            List<Word> cluster = new ArrayList<>();
            for (List<Word> wordCluster : listOfClusters) {
                for (Word clusterWord : wordCluster) {
//                    Rect.intersects(clusterWord.getPosition().boundingBox, word.getPosition().boundingBox) glitched, doesn't work
                    if (overlaps(word.getPosition().boundingBox, clusterWord.getPosition().boundingBox)) {
                        overlap = true;
                        Log.d("Clusters", "overlap between " + clusterWord.getTerm() + " and " + word.getTerm());
                        break;
                    }
                }
                if (overlap) {
                    wordCluster.add(word);
                    break;
                }
            }
            if (!overlap) {
                cluster.add(word);
                listOfClusters.add(cluster);
            }
        }
        Log.d("Clusters", "words " + DocumentManager.getInstance().getWordList().size() + ", clusters " + listOfClusters.size());
    }

    private boolean overlaps(Rect bb1, Rect bb2) {

        int l1 = Math.min(bb1.left, bb1.right;

//        int[] h = [bb2.left, bb2.right, bb2.bottom];
//
//        for (int i : bb) {
//            boolean horizontal = bb1.left <=
//        }
//
//
//
//        boolean left = bb1.left >= bb2.left && bb1.left <= bb2.right;
//        boolean right = bb1.right >= bb2.left && bb1.right <= bb2.right;
//        boolean top = bb1.top <= bb2.top && bb1.top >= bb2.bottom;
//        boolean bottom = bb1.bottom <= bb2.top && bb1.bottom >= bb2.bottom;
//        boolean intersect = (left || right) && (top || bottom);
//
//        boolean xContains = bb1.left <= bb2.left && bb1.right >= bb2.right;
//        boolean xContainsSwitch = bb2.left <= bb1.left && bb2.right >= bb1.right;
//        boolean yContains = bb1.top <= bb2.top && bb1.bottom >= bb2.bottom;
//        boolean yContainsSwitch = bb2.top <= bb1.top && bb2.bottom >= bb1.bottom;
//        boolean aContainsB = xContains && yContains;
//        boolean bContainsA = xContainsSwitch && yContainsSwitch;
//
//        boolean horizontalIntersection = xContains && (top || bottom);
//        boolean verticalIntersection = yContains && (left || right);
//
//        return intersect || aContainsB || bContainsA || verticalIntersection || horizontalIntersection;
    }
}

