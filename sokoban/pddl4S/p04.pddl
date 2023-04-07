;   #####
; ###   #
; #.g$  #
; ### $.#
; #.##$ #
; # # . ##
; #$ *$$.#
; #   .  #
; ########

(define (problem SOK04) 
    (:domain SOKOBAN)
    (:objects 
                f24 f25 f26
            f33 f34 f35 f36 
                f44 f45    
                    f55 f56 
        f62     f64     f66 
        f72 f73     f75 f76  
        f82 f83 f84     f86 f87 - floor

        d32 d46 d52 d65 d74 d77 d85 - destination
        
        b1 b2 b3 b4 b5 b6 b7 - box   
    )

    (:init
        ; path to right
        (path f24 f25 r)
        (path f25 f26 r)
        (path d32 f33 r)
        (path f33 f34 r)
        (path f34 f35 r)
        (path f35 f36 r)
        (path f44 f45 r)
        (path f45 d46 r)
        (path f55 f56 r)
        (path f64 d65 r)
        (path d65 f66 r)
        (path f72 f73 r)
        (path f73 d74 r)
        (path d74 f75 r)
        (path f75 f76 r)
        (path f76 d77 r)
        (path f82 f83 r)
        (path f83 f84 r)
        (path f84 d85 r)
        (path d85 f86 r)
        (path f86 f87 r)

        ; path to left
        (path f25 f24 l)
        (path f26 f25 l)
        (path f33 d32 l)
        (path f34 f33 l)
        (path f35 f34 l)
        (path f36 f35 l)
        (path f45 f44 l)
        (path d46 f45 l)
        (path f56 f55 l)
        (path d65 f64 l)
        (path f66 d65 l)
        (path f73 f72 l)
        (path d74 f73 l)
        (path f75 d74 l)
        (path f76 f75 l)
        (path d77 f76 l)
        (path f84 f82 l)
        (path f84 f83 l)
        (path d85 f84 l)
        (path f86 d85 l)
        (path f87 f86 l)

        ; path to down
        (path f24 f34 d)
        (path f25 f35 d)
        (path f26 f36 d)
        (path f34 f44 d)
        (path f35 f45 d)
        (path f36 d46 d)
        (path f45 f55 d)
        (path d46 f56 d)
        (path d52 f62 d)
        (path f55 d65 d)
        (path f56 f66 d)
        (path f62 f72 d)
        (path f64 d74 d)
        (path d65 f75 d)
        (path f66 f76 d)
        (path f72 f82 d)
        (path f73 f83 d)
        (path d74 f84 d)
        (path f75 d85 d)
        (path f76 f86 d)
        (path d77 f87 d)

        ; path to up
        (path f34 f24 u)
        (path f35 f25 u)
        (path f36 f26 u)
        (path f44 f34 u)
        (path f45 f35 u)
        (path d46 f36 u)
        (path f55 f45 u)
        (path f56 d46 u)
        (path f62 d52 u)
        (path d65 f55 u)
        (path f66 f56 u)
        (path f72 f62 u)
        (path d74 f64 u)
        (path f75 d65 u)
        (path f76 f66 u)
        (path f82 f72 u)
        (path f83 f73 u)
        (path f84 d74 u)
        (path d85 f75 u)
        (path f86 f76 u)
        (path f87 d77 u)

        ; empty
        (empty f24)
        (empty f25)
        (empty f26)
        (empty d32)
        (empty f35)
        (empty f36)
        (empty f44)
        (empty d46)
        (empty d52)
        (empty f56)
        (empty f62)
        (empty f64)
        (empty d65)
        (empty f66)
        (empty f73)
        (empty d77)
        (empty f82)
        (empty f83)
        (empty f84)
        (empty d85)
        (empty f86)
        (empty f87)

        ; boxes (on and withbox)
        (on b1 f34)
        (withbox f34)

        (on b2 f45)
        (withbox f45)

        (on b3 f55)
        (withbox f55)

        (on b4 f72)
        (withbox f72)

        (on b5 d74)
        (withbox d74)

        (on b6 f75)
        (withbox f75)

        (on b7 f76)
        (withbox f76)

        ; guard
        (on g f33)
    )

    (:goal (and
        (withbox d32)
        (withbox d46)
        (withbox d52)
        (withbox d65)
        (withbox d74)
        (withbox d77)
        (withbox d85)
    ))

)
