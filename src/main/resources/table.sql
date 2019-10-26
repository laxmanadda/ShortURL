CREATE TABLE `shortnertable` (
  `URL` varchar(767) NOT NULL,
  `ShortURL` varchar(30) NOT NULL,
  PRIMARY KEY (`URL`),
  UNIQUE KEY `URL_UNIQUE` (`URL`),
  UNIQUE KEY `ShortURL_UNIQUE` (`ShortURL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
