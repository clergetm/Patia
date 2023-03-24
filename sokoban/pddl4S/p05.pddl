;   ####
;   #  #
; ### .#
; #  * #
; # #@ #
; # $* #
; ##   #
;  #####

(define (problem SOK05) 
    (:domain SOKOBAN)
    (:objects 
                f24 f25
                f34 
        f42 f43     f45
        f52     f54 f55
        f62 f63     f65
            f73 f74 f75 - floor

        d35 d44 d64 - destination

        b1 b2 b3 - box
    )

    (:init
        ; path to right
        (path f24 f25 r)
        (path f34 d35 r)
        (path f42 f43 r)
        (path f43 d44 r)
        (path d44 f45 r)
        (path f54 f55 r)
        (path f62 f63 r)
        (path f63 d64 r)
        (path d64 f65 r)
        (path f73 f74 r)
        (path f74 f75 r)

        ; path to left
        (path f25 f24 l)
        (path d35 f34 l)
        (path f43 f42 l)
        (path d44 f43 l)
        (path f45 d44 l)
        (path f55 f54 l)
        (path f63 f62 l)
        (path d64 f63 l)
        (path f65 d64 l)
        (path f74 f73 l)
        (path f75 f74 l)

        ; path to down
        (path f24 f34 d)
        (path f25 d35 d)
        (path f34 d44 d)
        (path d35 f45 d)
        (path f42 f52 d)
        (path d44 f54 d)
        (path f45 f55 d)
        (path f52 f62 d)
        (path f54 d64 d)
        (path f55 f65 d)
        (path f63 f73 d)
        (path d64 f74 d)
        (path f65 f75 d)

        ; path to up
        (path f34 f24 u)
        (path d35 f25 u)
        (path d44 f34 u)
        (path f45 d35 u)
        (path f52 f42 u)
        (path f54 d44 u)
        (path f55 f45 u)
        (path f62 f52 u)
        (path d64 f54 u)
        (path f65 f55 u)
        (path f73 f63 u)
        (path f74 d64 u)
        (path f75 f65 u)

        ; empty
        (empty f24)
        (empty f25)
        (empty f34)
        (empty d35)
        (empty f42)
        (empty f43)
        (empty f45)
        (empty f52)
        (empty f55)
        (empty f62)
        (empty f65)
        (empty f73)
        (empty f74)
        (empty f75)
        
        ; boxes
        (on b1 d44)
        (withbox d44)

        (on b2 f63)
        (withbox f63)

        (on b3 d64)
        (withbox d64)

        ; guard
        (on g f54)
        )

    (:goal (and
        (withbox d35)
        (withbox d44)
        (withbox d64)
        
    ))

)
