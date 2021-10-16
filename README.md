# SmoothAlgo-microservices

Une application SpringBoot de gestion des couvertures santé des assurés sociaux par la CNAM.
L’application est découpée en 3 micro services : un micro service Benef (gestion des bénéficiaires de soins), 
un micro service Medim (gestion des médicament pris en charge) 
un micro service eligRefund(qui calcule l’éligibilité et remboursement du patient en interrogeantles deux micro services précédents , et en retournant une réponse à la pharmacie).
Utilisation de Keycloak comme un identity provider pour le login des utilisateurs, il sert à authentifier les pharmaciens et les administrateurs de l’application.
