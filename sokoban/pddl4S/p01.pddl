; #@   $.#
; A five position problem in one line with one box
; next to the destination

(define (problem SOK01)
    (:domain SOKOBAN)
    (:objects
        f1 f2 f3 f4 - floor
        dest - destination
        b - box
    )

    (:init
        ; path to right
        (path f1 f2 r)
        (path f2 f3 r)
        (path f3 f4 r)
        (path f4 dest r)

        ; path to left
        (path f2 f1 l)
        (path f3 f2 l)
        (path f4 f3 l)
        (path dest f4 l)

        ; empty
        (empty f2)
        (empty f3)
        (empty dest)

        ; box
        (on b f4)
        (withbox f4)

        ; guard
        (on g f1)
    )

    (:goal
        (and
            (withbox dest)
        )
    )

)