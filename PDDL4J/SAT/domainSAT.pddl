;Header and description

(define (domain SATSolving)

    (:requirements :strips :typing)
    (:types 
        clause variable
    )
    (:predicates 
        (contains ?v - variable ?c - clause)
        (containsNot ?v - variable ?c - clause)
        (solved ?c - clause)
        (isTrue ?v - variable)
        (isFalse ?v - variable) ;not(isTrue)
        (asNoValue ?v - variable) ;once we defined it we can't move it
    )

    (:action putTrue
        :parameters (?v - variable)
        :precondition (and
                (asNoValue ?v) 
        )
        :effect (and 
                (not (asNoValue ?v))
                (isTrue ?v))
    )
    
    (:action putFalse
        :parameters (?v - variable)
        :precondition (and
                (asNoValue ?v) 
        )
        :effect (and 
                (not (asNoValue ?v))
                (isFalse ?v))
    )
    
    (:action solveByTrue
        :parameters (?c - clause ?v - variable)
        :precondition (and 
                (contains ?v ?c)
                (isTrue ?v))
        :effect (and 
                (solved ?c))
    )
    
    (:action solveByFalse
        :parameters (?c - clause ?v - variable)
        :precondition (and 
                (containsNot ?v ?c)
                (isFalse ?v))
        :effect (and 
                (solved ?c))
    )

)