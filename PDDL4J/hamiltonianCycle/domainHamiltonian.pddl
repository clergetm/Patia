(define (domain hamiltonian)

    (:requirements :strips :typing)

    (:types 
        node vertice
    )

    (:predicates 
        (connected ?n - node ?n - node ?v - vertice)
        (notAlreadyPass ?n - node)
        (pass ?n - node)
        (notStarted)
    )

    (:action startBy
        :parameters (?n - node)
        :precondition (and 
                (notStarted)
                )
        :effect (and 
                (not (notStarted))
                (pass ?n)
                )
    )
    
    (:action passBy
        :parameters (?v - vertice ?n1 - node ?n2 - node)
        :precondition (and 
                (connected ?n1 ?n2 ?v)
                (pass ?n1)
                (notAlreadyPass ?n2)
                )
        :effect (and 
                (pass ?n2)
                (not (notAlreadyPass ?n2))
                )
    )
    
)