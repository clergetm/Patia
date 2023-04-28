(define (problem sat-0-2) (:domain SATSolving)
    (:objects
        c1 c2 c3 c4 c5 - clause
        a b c d e f g h  - variable
        ; (a,b)^(-a,c,d)^(e,f,g,-h)^(-d,-e)^(e,-f,-g)
    )

    (:init
        (asNoValue a)
        (asNoValue b)
        (asNoValue c)
        (asNoValue d)
        (asNoValue e)
        (asNoValue f)
        (asNoValue g)
        (asNoValue h)

        ;clause 1 : (a,b)
        (contains a c1)
        (contains b c1)

        ;clause 2 : (-a,c,d)
        (containsNot a c2)
        (contains c c2)
        (contains d c2)

        ;clause 3 : (e,f,g,-h)
        (contains e c3)
        (contains f c3)
        (contains g c3)
        (containsNot h c3)

        ;clause 4 : (-d,-e)
        (containsNot d c4)
        (containsNot e c4)

        ;clause 5 : (e,-f,-g)
        (contains e c5)
        (containsNot f c5)
        (containsNot g c5)
    )

    (:goal
        (and
        (solved c1)
        (solved c2)
        (solved c3)
        (solved c4)
        (solved c5)
        )
    )

)