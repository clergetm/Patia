(define (problem purs-0-1) (:domain Pursuit2)
    (:objects
        n1 n2 n3 n4 n5 - node
        p1 p2 - pursuer
    )

    (:init
        ;Same as in exercice sheet
        ;      n5
        ;       |
        ; n1---n2---n4
        ;       |   |
        ;       -n3--

        ;Connection (bylateral):
        ;n1
        (connected n1 n2)
        (connected n2 n1)
        (oneConnection n1)
        (notNConnection n1)
        ;n2
        (connected n2 n4)
        (connected n4 n2)
        (connected n2 n3)
        (connected n3 n2)
        (connected n2 n5)
        (connected n5 n2)
        (nConnection n2)
        ;n3 n4
        (connected n3 n4)
        (connected n4 n3)
        (twoConnection n3)
        (twoConnection n4)
        (notNConnection n3)
        (notNConnection n4)
        ;n5 (already connected to n2)
        (oneConnection n5)
        (notNConnection n5)
        
        ;notSame nodes (bylateral)
        (notSameN n1 n2)
        (notSameN n2 n1)
        (notSameN n1 n3)
        (notSameN n3 n1)
        (notSameN n1 n4)
        (notSameN n4 n1)
        (notSameN n1 n5)
        (notSameN n5 n1)

        (notSameN n2 n3)
        (notSameN n3 n2)
        (notSameN n2 n4)
        (notSameN n4 n2)
        (notSameN n2 n5)
        (notSameN n5 n2)

        (notSameN n3 n4)
        (notSameN n4 n3)
        (notSameN n3 n5)
        (notSameN n5 n3)

        (notSameN n4 n5)
        (notSameN n5 n4)

        ;notSame pursuer (bylateral)
        (notSameP p1 p2)
        (notSameP p2 p1)

        ;init state
        (on n1 p1)
        (on n1 p2)
        (cover n1)

    )

    (:goal
        (and
        (cover n1)
        (cover n2)
        (cover n3)
        (cover n4)
        (cover n5)
        )
    )

)