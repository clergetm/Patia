(define (problem sat-0-1) (:domain SATSolving)
    (:objects
        c1 c2 c3 - clause
        a b c d  - variable
    )

    (:init
        (asNoValue a)
        (asNoValue b)
        (asNoValue c)
        (asNoValue d)

        ;clause 1 : (a,b,-d)
        (contains a c1)
        (contains b c1)
        (containsNot d c1)

        ;clause 2 : (d)
        (contains d c2)

        ;clause 3 : (b,-c)
        (contains b c3)
        (containsNot c c3)
    )

    (:goal
        (and
        (solved c1)
        (solved c2)
        (solved c3)
        )
    )

)