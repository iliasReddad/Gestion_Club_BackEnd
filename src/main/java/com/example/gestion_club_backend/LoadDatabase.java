//package com.example.gestion_club_backend;
//
//import com.example.gestion_club_backend.Model.Club;
//import com.example.gestion_club_backend.Model.Evenement;
//import com.example.gestion_club_backend.Repository.ClubRepository;
//import com.example.gestion_club_backend.Repository.EvenementRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//
//    @Bean
//    CommandLineRunner initDatabase(ClubRepository repository , EvenementRepository evenementRepository) {
//
//        return args -> {
//
//            log.info("Preloading " + evenementRepository.save(
//                    new Evenement("Semaine d'orientation","le Club UIR 17 vous donne rendez vous le 16,20 et 23 décembre pour trois conférences chacune portant sur une spécialité proposée par l’école supérieur de l’informatique et du numérique de l’UIR. ","scontent.frba1-1.fna.fbcdn.net/v/t39.30808-6/266649649_775464907186870_295504543198288161_n.jpg?_nc_cat=108&ccb=1-5&_nc_sid=a26aad&_nc_ohc=gfGf028XJC0AX8bRhbo&_nc_ht=scontent.frba1-1.fna&oh=00_AT9z-5CHVhjRMyXCiZmV-X2EEQsR1K5cXtTgeNn27lQTyQ&oe=61E4CFA2","Le programme est le suivant\n" +
//                            "                          • (ISI) : jeudi 16 décembre 2021 à 16H00 \n" +
//                            "                           • (SSI) : lundi 20 décembre 2021 à 16H00\n" +
//                            "                           •  Big Data, AI & Data Science: jeudi 23 décembre 2021 à 14H00\n","Durant chaque conférence, un professeur présentera le cursus académique de la filière, ensuite un Alumni de l’école informatique de l’UIR partagera son expérience professionnelle.")));
//
//            ;
//
//
//
//        };
//    }
//}