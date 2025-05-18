This repo contains a java file CreateVcf that accepts arguments for a personal business cards in the following order:
- Full Name
- E-mail
- Phone
- Home Address
- Work Address
- Profile Picture

Running the file with returns the contents of a VCF file. 
For example:
```bash
javac main.java.homework.task1/CreateVcf.java && java main.java.homework.task1/CreateVcf "Milen Petrov" "milenp@fmi.uni-sofia.bg" "0878 987 654" "Sofia, James Baucher 5" "Sofia, Mladost 1A" "https://0.academia-photos.com/2026693/668180/829410/s200_milen.petrov.jpg" > main.java.homework.task1/business_cards/milen_petrov.vcf
```

My personal business card is located in `business_cards/adrian_degenkolb.vcf`