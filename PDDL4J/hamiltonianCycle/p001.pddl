(define (problem hamil-0-1) (:domain hamiltonian)
(:objects 
    A B C D - node
    I II III IV V VI VII - vertice
)

(:init
    ;the graph (bilateral)
    ;A - B
    (connected A B I)
    (connected B A I)
    (connected A B VII)
    (connected B A VII)
    ;A - D
    (connected A D VI)
    (connected D A VI)
    ;B - C
    (connected C B II)
    (connected B C II)
    (connected C B III)
    (connected B C III)
    ;B - D
    (connected D B V)
    (connected B D V)
    ;C - D
    (connected D C IV)
    (connected C D IV)

    ;set to 0
    (notAlreadyPass A)
    (notAlreadyPass B)
    (notAlreadyPass C)
    (notAlreadyPass D)

    ;Start :
    (notStarted)

)

(:goal (and
    (pass A)
    (pass B)
    (pass C)
    (pass D)
))

)
