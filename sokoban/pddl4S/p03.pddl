; ####
; #  #
; #  #####
; #  *   #
; ##*$ $ #
; #  .# ##
; #.  @ # 
; #######

; This problem is unsolvable with our domain
; Trust me don’t launch it, you’ll lose 30 minutes of your time

(define (problem SOK03)
    (:domain SOKOBAN)
    (:objects
        f11 f12 f21 f22 f31 f32 f34 f35 f36 f43 f44 f45 f46 f51 f52 f55 f62 f63 f64 f65 - floor

        d33 d42 d53 d61 - destination
        b1 b2 b3 b4 - box
    )

    (:init
        ; path to right
        (path f11 f12 r)
        (path f21 f22 r)
        (path f31 f32 r)
        (path f32 d33 r)
        (path d33 f34 r)
        (path f34 f35 r)
        (path f35 f36 r)
        (path d42 f43 r)
        (path f43 f44 r)
        (path f44 f45 r)
        (path f45 f46 r)
        (path f51 f52 r)
        (path f52 d53 r)
        (path d61 f62 r)
        (path f62 f63 r)
        (path f63 f64 r)
        (path f64 f65 r)

        ; path to left
        (path f12 f11 l)
        (path f22 f21 l)
        (path f32 f31 l)
        (path d33 f32 l)
        (path f34 d33 l)
        (path f35 f34 l)
        (path f36 f35 l)
        (path f43 d42 l)
        (path f44 f43 l)
        (path f45 f44 l)
        (path f46 f45 l)
        (path f52 f51 l)
        (path d53 f52 l)
        (path f62 d61 l)
        (path f63 f62 l)
        (path f64 f63 l)
        (path f65 f64 l)

        ; path to down
        (path f11 f21 d)
        (path f12 f22 d)
        (path f21 f31 d)
        (path f22 f32 d)
        (path f32 d42 d)
        (path d33 f43 d)
        (path f34 f44 d)
        (path f35 f45 d)
        (path f36 f46 d)
        (path d42 f52 d)
        (path f43 d53 d)
        (path f45 f55 d)
        (path f51 d61 d)
        (path f52 f62 d)
        (path d53 f63 d)
        (path f55 f65 d)

        ; path to up
        (path f21 f11 u)
        (path f22 f12 u)
        (path f31 f21 u)
        (path f32 f22 u)
        (path d42 f32 u)
        (path f43 d33 u)
        (path f44 f34 u)
        (path f45 f35 u)
        (path f46 f36 u)
        (path f52 d42 u)
        (path d53 f43 u)
        (path f55 f45 u)
        (path d61 f51 u)
        (path f62 f52 u)
        (path f63 d53 u)
        (path f65 f55 u)

        ; empty
        (empty d53)
        (empty d61)
        (empty f11)
        (empty f12)
        (empty f21)
        (empty f22)
        (empty f31)
        (empty f32)
        (empty f34)
        (empty f35)
        (empty f36)
        (empty f44)
        (empty f46)
        (empty f51)
        (empty f52)
        (empty f55)
        (empty f62)
        (empty f63)
        (empty f65)

        ; boxes
        (on b1 d33)
        (withbox d33)

        (on b2 d42)
        (withbox d42)

        (on b3 f43)
        (withbox f43)

        (on b4 f45)
        (withbox f45)

        ; guard
        (on g f64)
    )

    (:goal
        (and
            (withbox d33)
            (withbox d42)
            (withbox d53)
            (withbox d61)

        )
    )

)