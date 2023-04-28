;Header and description

(define (domain Pursuit2)

    (:requirements :strips :typing)
    (:types 
        node pursuer
    )

    (:predicates 
        (on ?n - node ?p - pursuer)
        (cover ?n - node)
        (connected ?n1 - node ?n2 - node)
        (oneConnection ?n1 - node)
        (twoConnection ?n1 - node)
        (nConnection ?n1 -node)
        (notSameP ?p1 - pursuer ?p2 - pursuer)
        (notSameN ?n1 - node ?n2 - node)
    )

    ;move from n1 to n2 while n1 is only connected to n2
    (:action moveCoverOc
        :parameters (?n1 - node ?n2 - node ?p - pursuer)
        :precondition (and 
                (on ?n1 ?p)
                (connected ?n1 ?n2)
                (oneConnection ?n1)
                )
        :effect (and 
                (not (on ?n1 ?p))
                (on ?n2 ?p)
                (cover ?n2)
                )
    )

    ;Move from n1 to n2 while n1 is also connected to n3 (and only connected to the two of them)
    (:action moveCoverTc
        :parameters (?n1 - node ?n2 - node ?n3 - node ?p - pursuer)
        :precondition (and 
                (on ?n1 ?p)
                (connected ?n1 ?n2)
                (connected ?n2 ?n3)
                (notSameN ?n2 ?n3)
                (twoConnection ?n1)
                (cover ?n3)
                )
        :effect (and 
                (not (on ?n1 ?p))
                (on ?n2 ?p)
                (cover ?n2)
                )
    )
    
    ;We allow to move and let the node cover while there is n connection (at least 2) if and only if there is another pursuer with us
    (:action moveCoverNc
        :parameters (?n1 - node ?n2 - node ?p1 - pursuer ?p2 - pursuer)
        :precondition (and 
                (on ?n1 ?p1)
                (on ?n1 ?p2)
                (notSameP ?p1 ?p2)
                (connected ?n1 ?n2)
                (nConnection ?n1)
                )
        :effect (and 
                (not (on ?n1 ?p1))
                (on ?n2 ?p1)
                (cover ?n2)
                )
    )

    ;Move from n1 to n2 but uncover n2
    (:action moveUncover
        :parameters (?n1 - node ?n2 - node ?p - pursuer)
        :precondition (and 
                (on ?n1 ?p)
                (connected ?n1 ?n2)
                )
        :effect (and 
                (not (on ?n1 ?p))
                (not (cover ?n1))
                (on ?n2 ?p)
                (cover ?n2)
                )
    )
    
)