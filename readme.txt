
Beschreibung

RadCloud[1] ist eine Methode um Word Clouds von mehreren Quellen in einer Ansicht darzustellen. Basierend auf RadViz werden die Wörter in einem überlappungsfreien Layout anhand der Häufigkeit nach Quelle ausgerichtet. Die Anzahl der Wörter in den einzelnen Quellen wird durch gestapelte Barcharts unter den Wörtern symbolisiert. 


Implementierung

Die implementierung erfolgt in Android durch Canvas.
Die Textquellen werden gruppiert eingelesen, dabei ist der Text bereits tokenized (natürlicher Text). Eine Stop Word List für Englisch wird benutzt um irrelevante Wörter (z.B bindewörter wie “und” und  “oder”) zu filtern. 
Die Wort Relevanz ist basierend auf der Wort Häufigkeit (tf - term frequency) multipliziert mit der inversen Dokument Häufigkeit (idf - inverse document frequency) berechnet. Dieser tf * idf Wert bedeutet, dass ein Wort welches in allen Dokumenten vorkommt, für ein spezifisches Dokument weniger relevant ist, als ein Wort welches nur in diesem Dokument vorkommt.
Die Anzahl der Wörter für welche, diese Berechnungen durchgeführt werden, ist beschränkt auf 100 Wörter pro Dokument.
Die Größe der Wörter reflektiert die Relevanz, die Farbe ist interpoliert zwischen den Kategorien wo das Wort vorkommt, weiters zeigt unter jedem Wort eine Barchart die Häufigkeit in den unterschiedlichen Quellen. Weiters wird die Wort Position anhand eines Spiral Layout Algorithmus[2] berechnet. Um Überlappungen zu vermeiden, verschieben sich Wörter welche überlappen um eine möglichst geringe Distanz.

Referenzen

[1]Burch, Michael, et al. "RadCloud: Visualizing multiple texts with merged word clouds." Information Visualisation (IV), 2014 18th International Conference on. IEEE, 2014.
[2]Strobelt, Hendrik, et al. "Rolled-out Wordles: A Heuristic Method for Overlap Removal of 2D Data Representatives." Computer Graphics Forum. Vol. 31. No. 3pt3. Blackwell Publishing Ltd, 2012.