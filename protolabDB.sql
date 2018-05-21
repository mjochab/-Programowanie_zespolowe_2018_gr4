-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 21 Maj 2018, 17:56
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
  `Haslo` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `dane_logowania`
--

INSERT INTO `dane_logowania` (`ID_konta`, `Login`, `Haslo`) VALUES
(1, 'admin', 'admin'),
(2, 'user1', 'aaa'),
(3, 'user2', 'bbb'),
(4, 'user3', 'ccc'),
(5, 'user4', 'ddd'),
(6, 'user5', 'eee'),
(7, 'user6', 'fff'),
(8, 'user7', 'ggg'),
(9, 'user8', 'hhh'),
(10, 'user9', 'iii'),
(11, 'user10', 'jjj'),
(12, 'user11', 'kkk'),
(13, 'user12', 'lll'),
(14, 'user13', 'mmm'),
(15, 'user14', 'nnn');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `przedmioty`
--

CREATE TABLE `przedmioty` (
  `ID_przedmiotu` int(11) NOT NULL,
  `Nazwa` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Rodzaj` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `Ilosc` int(11) NOT NULL,
  `Status` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `przedmioty`
--

INSERT INTO `przedmioty` (`ID_przedmiotu`, `Nazwa`, `Rodzaj`, `Ilosc`, `Status`) VALUES
(1, 'monitor', 'sprzęt komputerowy', 40, 'w magazynie'),
(2, 'klawiatura', 'sprzęt komputerowy', 30, 'w magazynie'),
(3, 'mysz komputerowa', 'sprzęt komputerowy', 15, 'w magazynie'),
(4, 'głośniki', 'sprzęt komputerowy', 25, 'w magazynie'),
(5, 'drukarka', 'sprz?t komputerowy', 20, 'w magazynie'),
(6, 'skaner', 'sprz?t komputerowy', 35, 'w magazynie'),
(7, 'fax', 'sprzet komputerowy', 50, 'w magazynie');

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
(2, 3, 7, '2018-05-22', '2018-05-25', 10);

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
(2, 'admin'),
(1, 'student'),
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
(4, 1, 'Marek', 'Nowak', 745861234, 'aads@op.pl', 59234851346);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `dane_logowania`
--
ALTER TABLE `dane_logowania`
  ADD PRIMARY KEY (`ID_konta`),
  ADD UNIQUE KEY `Haslo_UNIQUE` (`Haslo`);

--
-- Indeksy dla tabeli `przedmioty`
--
ALTER TABLE `przedmioty`
  ADD PRIMARY KEY (`ID_przedmiotu`);

--
-- Indeksy dla tabeli `rezerwacje`
--
ALTER TABLE `rezerwacje`
  ADD PRIMARY KEY (`idRezerwacji`),
  ADD UNIQUE KEY `idRezerwacji_UNIQUE` (`idRezerwacji`),
  ADD KEY `ID_uzytkownika_idx` (`ID_uzytkownika`),
  ADD KEY `ID_przedmiotu_idx` (`ID_przedmiotu`);

--
-- Indeksy dla tabeli `uprawnienia`
--
ALTER TABLE `uprawnienia`
  ADD PRIMARY KEY (`ID_uprawnienia`),
  ADD UNIQUE KEY `rodzajUprawnienia_UNIQUE` (`rodzajUprawnienia`);

--
-- Indeksy dla tabeli `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  ADD PRIMARY KEY (`ID_uzytkownika`),
  ADD UNIQUE KEY `pesel_UNIQUE` (`pesel`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD UNIQUE KEY `numerTel_UNIQUE` (`numerTel`),
  ADD UNIQUE KEY `ID_uprawnienia_UNIQUE` (`ID_uprawnienia`),
  ADD UNIQUE KEY `ID_uzytkownika_UNIQUE` (`ID_uzytkownika`);

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
  MODIFY `ID_przedmiotu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT dla tabeli `rezerwacje`
--
ALTER TABLE `rezerwacje`
  MODIFY `idRezerwacji` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  MODIFY `ID_uzytkownika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
