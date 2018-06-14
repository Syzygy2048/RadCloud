
Beschreibung

RadCloud[1] ist eine Methode um Word Clouds von mehreren Quellen in einer Ansicht darzustellen. Basierend auf RadViz werden die W�rter in einem �berlappungsfreien Layout anhand der H�ufigkeit nach Quelle ausgerichtet. Die Anzahl der W�rter in den einzelnen Quellen wird durch gestapelte Barcharts unter den W�rtern symbolisiert. 


Implementierung

Die implementierung erfolgt in Android durch Canvas.
Die Textquellen werden gruppiert eingelesen, dabei ist der Text bereits tokenized (nat�rlicher Text). Eine Stop Word List f�r Englisch wird benutzt um irrelevante W�rter (z.B bindew�rter wie �und� und  �oder�) zu filtern. 
Die Wort Relevanz ist basierend auf der Wort H�ufigkeit (tf - term frequency) multipliziert mit der inversen Dokument H�ufigkeit (idf - inverse document frequency) berechnet. Dieser tf * idf Wert bedeutet, dass ein Wort welches in allen Dokumenten vorkommt, f�r ein spezifisches Dokument weniger relevant ist, als ein Wort welches nur in diesem Dokument vorkommt.
Die Anzahl der W�rter f�r welche, diese Berechnungen durchgef�hrt werden, ist beschr�nkt auf 100 W�rter pro Dokument.
Die Gr��e der W�rter reflektiert die Relevanz, die Farbe ist interpoliert zwischen den Kategorien wo das Wort vorkommt, weiters zeigt unter jedem Wort eine Barchart die H�ufigkeit in den unterschiedlichen Quellen. Weiters wird die Wort Position anhand eines Spiral Layout Algorithmus[2] berechnet. Um �berlappungen zu vermeiden, verschieben sich W�rter welche �berlappen um eine m�glichst geringe Distanz.

Referenzen

[1]Burch, Michael, et al. "RadCloud: Visualizing multiple texts with merged word clouds." Information Visualisation (IV), 2014 18th International Conference on. IEEE, 2014.
[2]Strobelt, Hendrik, et al. "Rolled-out Wordles: A Heuristic Method for Overlap Removal of 2D Data Representatives." Computer Graphics Forum. Vol. 31. No. 3pt3. Blackwell Publishing Ltd, 2012.