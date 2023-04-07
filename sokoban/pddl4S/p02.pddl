; #######
; #     #
; #@   $#
; #    .#
; #######
; A five by three space with the box next to the 
; destination

(define (problem SOK02)
    (:domain SOKOBAN)
    (:objects
        f1 f2 f3 f4 f5 f6 f7 f8 f9 f10 f11 f12 f13 f14 - floor
        dest - destination
        b - box
    )

    (:init
        ; path to right
        (path f1 f2 r)
        (path f2 f3 r)
        (path f3 f4 r)
        (path f4 f5 r)
        (path f6 f7 r)
        (path f7 f8 r)
        (path f8 f9 r)
        (path f9 f10 r)
        (path f11 f12 r)
        (path f12 f13 r)
        (path f13 f14 r)
        (path f14 dest r)

        ; path to left
        (path f2 f1 l)
        (path f3 f2 l)
        (path f4 f3 l)
        (path f5 f4 l)
        (path f7 f6 l)
        (path f8 f7 l)
        (path f9 f8 l)
        (path f10 f9 l)
        (path f12 f11 l)
        (path f13 f12 l)
        (path f14 f13 l)
        (path dest f14 l)

        ; path to down
        (path f1 f6 d)
        (path f2 f7 d)
        (path f3 f8 d)
        (path f4 f9 d)
        (path f5 f10 d)
        (path f6 f11 d)
        (path f7 f12 d)
        (path f8 f13 d)
        (path f9 f14 d)
        (path f10 dest d)

        ; path to up
        (path f6 f1 u)
        (path f7 f2 u)
        (path f8 f3 u)
        (path f9 f4 u)
        (path f10 f5 u)
        (path f11 f6 u)
        (path f12 f7 u)
        (path f13 f8 u)
        (path f14 f9 u)
        (path dest f10 u)

        ; empty
        (empty f1)
        (empty f2)
        (empty f3)
        (empty f4)
        (empty f5)
        (empty f7)
        (empty f8)
        (empty f9)

        (empty f11)
        (empty f12)
        (empty f13)
        (empty f14)
        (empty dest)

        ; box
        (on b f10)
        (withbox f10)

        ; guard
        (on g f6)

    )

    (:goal(and
            (withbox dest)
        )
    )

)