(define (problem p001) (:domain PURSUIT)
(:objects 
    police1 police2 - pursuer
    A B C D E - node
)

(:init
    (connected A B)
    (connected B A)

    (connected B C)
    (connected C B)

    (connected B D)
    (connected D B)

    (connected B E)
    (connected E B)
    
    (connected D E)
    (connected E D)

    (intruderOn B)
    (pursuerOn police1 A)
    (pursuerOn police2 A)
 
   (not (freeNode A))    
)

(:goal (and
    (caught)
))

)
