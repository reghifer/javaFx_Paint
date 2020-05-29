# rendu_IHM_Cartigny_Pierre

Partie 2, question 1
je pense que mon application respecte une architecture MVC, 
j'ai une classe Modele, dans laquelle je stoque les informations interne à l'application,
ma vue est symboliser par le fichier fxml, qui décrit la structure de l'application et enfin
les classes Controller et Drawer me servent à réaliser les modifications sur le modele et sur la vue.

Partie 2, question 2
le Undo fonctionne avec la touche Z et le redo avec la touche R, cela fonctionne pour le changement de couleur, la supprétion et l'ajout. Undo un mouvement ne fonctionne pas totalement, car l'origine des object est dans le coin supérieur gauche, or quand on fait un drag and drop la postion enrigster pour le redo est celle de l'object qui vient d'etre drag, donc le redo place l'origine de l'objet à l'endroit ou étais la souris.
