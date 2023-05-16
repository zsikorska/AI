% Elementy składowe piekarnika
posiada(piekarnik, drzwiczki).
posiada(piekarnik, wentylator).
posiada(piekarnik, grzałka).
posiada(piekarnik, lampka).
posiada(piekarnik, separator).
posiada(piekarnik, zasilanie).
posiada(piekarnik, timer).
posiada(piekarnik, panel_użytkownika).
posiada(piekarnik, czujnik_temperatury).

posiada(panel_użytkownika, wyświetlacz).
posiada(panel_użytkownika, przycisk(start)).
posiada(panel_użytkownika, przycisk(stop)).
posiada(panel_użytkownika, przycisk(wyłącz)).
posiada(panel_użytkownika, przycisk(blokada)).
posiada(panel_użytkownika, przycisk(wybór_funkcji)).
posiada(panel_użytkownika, przycisk(wybór_temperatury)).
posiada(panel_użytkownika, przycisk(wybór_czasu)).

posiada(drzwiczki, blokada).

% Informacje dotyczące przycisków
przycisk_jest_typu(start, dotykowy).
przycisk_jest_typu(stop, dotykowy).
przycisk_jest_typu(wyłącz, dotykowy).
przycisk_jest_typu(blokada, dotykowy).

przycisk_jest_typu(wybór_funkcji, pokrętło).
przycisk_jest_typu(wybór_temperatury, pokrętło).
przycisk_jest_typu(wybór_czasu, pokrętło).


% Dostępne funkcje
ma_funkcję(piekarnik, pieczenie).
ma_funkcję(piekarnik, czyszczenie_parą).

ma_funkcję(pieczenie, góra_dół).
ma_funkcję(pieczenie, tylko_góra).
ma_funkcję(pieczenie, tylko_dół).
ma_funkcję(pieczenie, termoobieg).
ma_funkcję(pieczenie, grill).


% Informacje o błędach
występuje_błąd(wyłącza_się_w_trakcie_pracy).
występuje_błąd(nie_podgrzewa).
występuje_błąd(czyszczenie_parą_nie_działa) :-
    ma_funkcję(piekarnik, czyszczenie_parą).

występuje_błąd(pieczenie_podwójne_nie_działa) :-
    ma_funkcję(piekarnik, pieczenie),
    ma_funkcję(pieczenie, góra_dół).

występuje_błąd(czas_się_nie_wyświetla).
występuje_błąd(piekarnik_nie_działa).

występuje_błąd(nie_można_naciskać_przycisków).


% Informacje o przyczynach błędów (przyczyna, błąd)
powoduje_błąd(brak_zasilania, wyłącza_się_w_trakcie_pracy).

powoduje_błąd(otwarte_drzwiczki, nie_podgrzewa).
powoduje_błąd(źle_ustawione_przyciski_sterowania, nie_podgrzewa).
powoduje_błąd(zadziałał_bezpiecznik, nie_podgrzewa).

powoduje_błąd(zbyt_wysoka_temperatura, czyszczenie_parą_nie_działa).

powoduje_błąd(brak_separatora, pieczenie_podwójne_nie_działa).

powoduje_błąd(brak_zasilania, czas_się_nie_wyświetla).
powoduje_błąd(brak_zasilania, piekarnik_nie_działa).

powoduje_błąd(obca_substancja, nie_można_naciskać_przycisków).
powoduje_błąd(wilgoć, nie_można_naciskać_przycisków) :-
    posiada(piekarnik, panel_użytkownika),
    posiada(panel_użytkownika, przycisk(_)),
    przycisk_jest_typu(_, dotykowy).

powoduje_błąd(blokada, nie_można_naciskać_przycisków) :-
    posiada(piekarnik, panel_użytkownika),
    posiada(panel_użytkownika, przycisk(blokada)).



% Informacje o częściach powodujących błędy (przyczyna, część)
część_powodująca_błąd(brak_zasilania, zasilanie).
część_powodująca_błąd(otwarte_drzwiczki, drzwiczki).
część_powodująca_błąd(źle_ustawione_przyciski_sterowania, panel_użytkownika).
część_powodująca_błąd(źle_ustawione_przyciski_sterowania, przycisk(_)).
część_powodująca_błąd(zadziałał_bezpiecznik, bezpiecznik).
część_powodująca_błąd(zbyt_wysoka_temperatura, czujnik_temperatury).
część_powodująca_błąd(brak_separatora, separator).
część_powodująca_błąd(obca_substancja, panel_użytkownika).
część_powodująca_błąd(obca_substancja, przycisk(_)).
część_powodująca_błąd(wilgoć, panel_użytkownika).
część_powodująca_błąd(wilgoć, przycisk(_)) :- przycisk_jest_typu(_, dotykowy).
część_powodująca_błąd(blokada, panel_użytkownika).
część_powodująca_błąd(blokada, przycisk(blokada)).


% Informacje o naprawach (rozwiązanie, błąd, przyczyna)
naprawia_błąd(podłączenie_zasilania, nie_podłączenie_do_gniazdka, wyłącza_się_w_trakcie_pracy).
naprawia_błąd(zamknięcie_drzwiczek, nie_podgrzewa, otwarte_drzwiczki).

naprawia_błąd(zresetowanie_piekarnika, nie_podgrzewa, źle_ustawione_przyciski_sterowania).
naprawia_błąd(zmiana_bezpiecznika, nie_podgrzewa, zadziałał_bezpiecznik).
naprawia_błąd(reset_obwodu, nie_podgrzewa, zadziałał_bezpiecznik).

naprawia_błąd(ostudzenie_piekarnika, czyszczenie_parą_nie_działa, zbyt_wysoka_temperatura).

naprawia_błąd(umieszczenie_poprawnie_separatora, pieczenie_podwójne_nie_działa, brak_separatora).

naprawia_błąd(podłączenie_zasilania, czas_się_nie_wyświetla, brak_zasilania).
naprawia_błąd(podłączenie_zasilania, piekarnik_nie_działa, brak_zasilania).

naprawia_błąd(usunięcie_obcej_substancji, nie_można_naciskać_przycisków, obca_substancja).
naprawia_błąd(usunięcie_wilgoci, nie_można_naciskać_przycisków, wilgoć).
naprawia_błąd(wyłączenie_blokady, nie_można_naciskać_przycisków, blokada).


rozwiąż_problem(Błąd, Przyczyna, Rozwiązanie) :-
    setof((Błąd, Przyczyna, Rozwiązanie), (
        występuje_błąd(Błąd),
        powoduje_błąd(Przyczyna, Błąd),
        naprawia_błąd(Rozwiązanie, Błąd, Przyczyna)
    ), Wyniki),
    member((Błąd, Przyczyna, Rozwiązanie), Wyniki).

rozwiąż_problem_complex(Błąd, Przyczyna, Rozwiązanie, Część) :-
    setof((Błąd, Przyczyna, Rozwiązanie, Część), (
        występuje_błąd(Błąd),
        powoduje_błąd(Przyczyna, Błąd),
        część_powodująca_błąd(Przyczyna, Część),
        naprawia_błąd(Rozwiązanie, Błąd, Przyczyna)
    ), Wyniki),
    member((Błąd, Przyczyna, Rozwiązanie, Część), Wyniki).


wyświetl_listę([]).
wyświetl_listę([Głowa|Ogon]) :-
  	write(Głowa), nl,
  	wyświetl_listę(Ogon).

znajdź_części_powodujące_błąd(Błąd) :-
  	findall(Część, (powoduje_błąd(Przyczyna, Błąd), część_powodująca_błąd(Przyczyna, Część)), Lista),
  	list_to_set(Lista, ListaBezDuplikatów),
  	wyświetl_listę(ListaBezDuplikatów).



/** <examples>

?- posiada(piekarnik, zasilanie).
?- posiada(panel_użytkownika, przycisk(blokada)).
?- posiada(piekarnik, mikrofalówka).
?- posiada(piekarnik, X).
?- posiada(_, przycisk(X)), przycisk_jest_typu(X, pokrętło).

?- przycisk_jest_typu(start, X).
?- przycisk_jest_typu(X, Y).

?- ma_funkcję(piekarnik, X), ma_funkcję(X, Y).


?- rozwiąż_problem_complex(Błąd, Przyczyna, Rozwiązanie, panel_użytkownika).
?- rozwiąż_problem_complex(Błąd, brak_separatora, Rozwiązanie, Część).

?- rozwiąż_problem(nie_podgrzewa, Przyczyna, Rozwiązanie).
?- rozwiąż_problem(nie_podgrzewa, brak_separatora, Rozwiązanie).
?- rozwiąż_problem(nie_podgrzewa, otwarte_drzwiczki, Rozwiązanie).

?- przycisk_jest_typu(X, dotykowy), rozwiąż_problem_complex(Błąd, Przyczyna, Rozwiązanie, przycisk(X)).

?- znajdź_części_powodujące_błąd(nie_podgrzewa).
?- znajdź_części_powodujące_błąd(wyłącza_się_w_trakcie_pracy).

*/
