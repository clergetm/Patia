(define (domain PURSUIT)

    ;remove requirements that are not needed
    (:requirements :strips :typing)

    (:types
        person - object
        pursuer intruder - person
        node
    )
    (:constants
        fredo - intruder; Fredo la menace is an intruder
        )
    (:predicates ;todo: define predicates here
        (connected ?n1 ?n2 - node) ; two node are connected by a vertice
        (intruderOn ?n - node)
        (pursuerOn ?p - pursuer ?n - node)
        (caught)
        (freeNode ?n - node)
    )

    (:action connect ; Connect two nodes from 'a' to 'b' and from 'b' to 'a'
        :parameters (?a - node ?b - node)
        :precondition(and
            (connected ?a ?b)
        )
        :effect ( and
            (connected ?b ?a)
        )
    )

    (:action moveIntruder
        :parameters (?from - node ?to - node)
        :precondition (and
            (intruderOn ?from)
            (connected ?from ?to)
            (freeNode ?to)
        )
        :effect (and
            (not (intruderOn ?from))
            (intruderOn ?to)
        )
    )

    (:action movePursuerFrom
        :parameters (?pursuer1 - pursuer ?pursuer2 - pursuer ?from - node ?to - node)
        :precondition (and
            (pursuerOn ?pursuer1 ?from)
            (pursuerOn ?pursuer2 ?from)
        )
        :effect (and
            (not (pursuerOn ?pursuer1 ?from))
            (pursuerOn ?pursuer1 ?to)
            (not (freeNode ?to))
        )
    )
    
    (:action movePursuerTo
        :parameters (?pursuer1 - pursuer ?pursuer2 - pursuer ?from - node ?to - node)
        :precondition (and
            (pursuerOn ?pursuer1 ?from)
            (pursuerOn ?pursuer2 ?to)
        )
        :effect (and
            (not (pursuerOn ?pursuer1 ?from))
            (pursuerOn ?pursuer1 ?to)
            (freeNode ?from)

        )
    )

    (:action caughtIntruder
        :parameters (?p - pursuer ?n - node)
        :precondition (and 
            (intruderOn ?n)
            (pursuerOn ?p ?n)
        )
        :effect (and 
            (caught)
        )
    )
    

)