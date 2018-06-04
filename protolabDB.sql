-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 04 Cze 2018, 02:31
-- Wersja serwera: 10.1.31-MariaDB
-- Wersja PHP: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `protolabdb`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dane_logowania`
--

CREATE TABLE `dane_logowania` (
  `ID_konta` int(11) NOT NULL,
  `Login` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Haslo` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Pass_Counter` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `dane_logowania`
--

INSERT INTO `dane_logowania` (`ID_konta`, `Login`, `Haslo`, `Pass_Counter`) VALUES
(1, 'admin', 'admin', 0),
(2, 'user1', 'aaa', 0),
(3, 'user2', 'bbb', 0),
(4, 'user3', 'ccc', 0),
(5, 'user4', 'ddd', 0),
(7, 'user6', 'fff', 0),
(8, 'user7', 'ggg', 0),
(9, 'user8', 'Dominik01', 0),
(10, 'user9', 'iii', 0),
(11, 'user10', 'jjj', 0),
(12, 'user11', 'kkk', 0),
(13, 'user12', 'lll', 0),
(14, 'user13', 'mmm', 0),
(15, 'user14', 'nnn', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `przedmioty`
--

CREATE TABLE `przedmioty` (
  `ID_przedmiotu` int(11) NOT NULL,
  `Nazwa` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `id_rodzaj` int(11) NOT NULL,
  `Ilosc` int(11) NOT NULL,
  `Status` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `przedmioty`
--

INSERT INTO `przedmioty` (`ID_przedmiotu`, `Nazwa`, `id_rodzaj`, `Ilosc`, `Status`) VALUES
(1, 'monitor', 1, 40, 'w magazynie'),
(2, 'klawiatura', 1, 30, 'w magazynie'),
(3, 'mysz komputerowa', 1, 15, 'w magazynie'),
(4, 'głośniki', 1, 25, 'w magazynie'),
(5, 'drukarka', 1, 20, 'w magazynie'),
(6, 'skaner', 1, 35, 'w magazynie'),
(7, 'fax', 1, 50, 'w magazynie'),
(8, 'drukarka3d', 1, 2, 'w magazynie');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rezerwacje`
--

CREATE TABLE `rezerwacje` (
  `idRezerwacji` int(11) NOT NULL,
  `ID_uzytkownika` int(11) NOT NULL,
  `ID_przedmiotu` int(11) NOT NULL,
  `od_kiedy` date NOT NULL,
  `do_kiedy` date NOT NULL,
  `ilosc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `rezerwacje`
--

INSERT INTO `rezerwacje` (`idRezerwacji`, `ID_uzytkownika`, `ID_przedmiotu`, `od_kiedy`, `do_kiedy`, `ilosc`) VALUES
(2, 3, 7, '2018-05-22', '2018-05-25', 10),
(3, 4, 2, '2018-05-22', '2018-05-23', 1),
(4, 2, 4, '2018-05-22', '2018-05-23', 1),
(5, 4, 6, '2018-05-22', '2018-05-23', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rodzaj_przedmiotu`
--

CREATE TABLE `rodzaj_przedmiotu` (
  `id_rodzaj` int(11) NOT NULL,
  `nazwa_typu` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `rodzaj_przedmiotu`
--

INSERT INTO `rodzaj_przedmiotu` (`id_rodzaj`, `nazwa_typu`) VALUES
(1, 'sprzet komputerowy'),
(2, 'urzadzenia agd');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uprawnienia`
--

CREATE TABLE `uprawnienia` (
  `ID_uprawnienia` int(11) NOT NULL,
  `rodzajUprawnienia` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `uprawnienia`
--

INSERT INTO `uprawnienia` (`ID_uprawnienia`, `rodzajUprawnienia`) VALUES
(1, 'student'),
(2, 'admin'),
(3, 'szef kola');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownicy`
--

CREATE TABLE `uzytkownicy` (
  `ID_uzytkownika` int(11) NOT NULL,
  `ID_uprawnienia` int(11) NOT NULL,
  `imie` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  `nazwisko` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  `numerTel` int(11) NOT NULL,
  `email` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  `pesel` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `uzytkownicy`
--

INSERT INTO `uzytkownicy` (`ID_uzytkownika`, `ID_uprawnienia`, `imie`, `nazwisko`, `numerTel`, `email`, `pesel`) VALUES
(2, 2, 'Jan', 'Kowalski', 874136899, 'kowalski@gmail.com', 45213654874),
(3, 3, 'Anna', 'Woźniak', 789654123, 'awozniak@op.pl', 74533216549),
(4, 1, 'Marek', 'Nowak', 745861234, 'aads@op.pl', 59234851346),
(9, 1, 'asd343', 'asd343', 45634563, 'wp.pl', 12345678);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `dane_logowania`
--
ALTER TABLE `dane_logowania`
  ADD PRIMARY KEY (`ID_konta`);

--
-- Indeksy dla tabeli `przedmioty`
--
ALTER TABLE `przedmioty`
  ADD PRIMARY KEY (`ID_przedmiotu`),
  ADD KEY `Rodzaj` (`id_rodzaj`);

--
-- Indeksy dla tabeli `rezerwacje`
--
ALTER TABLE `rezerwacje`
  ADD PRIMARY KEY (`idRezerwacji`),
  ADD UNIQUE KEY `idRezerwacji_UNIQUE` (`idRezerwacji`),
  ADD KEY `ID_uzytkownika_idx` (`ID_uzytkownika`),
  ADD KEY `ID_przedmiotu_idx` (`ID_przedmiotu`);

--
-- Indeksy dla tabeli `rodzaj_przedmiotu`
--
ALTER TABLE `rodzaj_przedmiotu`
  ADD PRIMARY KEY (`id_rodzaj`);

--
-- Indeksy dla tabeli `uprawnienia`
--
ALTER TABLE `uprawnienia`
  ADD PRIMARY KEY (`ID_uprawnienia`);

--
-- Indeksy dla tabeli `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  ADD PRIMARY KEY (`ID_uzytkownika`),
  ADD UNIQUE KEY `pesel_UNIQUE` (`pesel`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD UNIQUE KEY `numerTel_UNIQUE` (`numerTel`),
  ADD UNIQUE KEY `ID_uzytkownika_UNIQUE` (`ID_uzytkownika`),
  ADD KEY `ID_uprawnienia_UNIQUE` (`ID_uprawnienia`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `dane_logowania`
--
ALTER TABLE `dane_logowania`
  MODIFY `ID_konta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT dla tabeli `przedmioty`
--
ALTER TABLE `przedmioty`
  MODIFY `ID_przedmiotu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT dla tabeli `rezerwacje`
--
ALTER TABLE `rezerwacje`
  MODIFY `idRezerwacji` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `rodzaj_przedmiotu`
--
ALTER TABLE `rodzaj_przedmiotu`
  MODIFY `id_rodzaj` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  MODIFY `ID_uzytkownika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `rezerwacje`
--
ALTER TABLE `rezerwacje`
  ADD CONSTRAINT `ID_przedmiotu` FOREIGN KEY (`ID_przedmiotu`) REFERENCES `przedmioty` (`ID_przedmiotu`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `ID_uzytkownika` FOREIGN KEY (`ID_uzytkownika`) REFERENCES `uzytkownicy` (`ID_uzytkownika`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  ADD CONSTRAINT `ID_uprawnienia` FOREIGN KEY (`ID_uprawnienia`) REFERENCES `uprawnienia` (`ID_uprawnienia`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
