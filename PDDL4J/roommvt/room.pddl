(define (problem ROOM-0-1)
  (:domain ROOM)
  (:objects
    r1 r2 - location
    ro1 - robot
    c1 -container
  )

  (:init
    (at ro1 r1)
    (adjacent r1 r2)
    (in c1 r1)
    (unloaded ro1)
  )

  (:goal
    (and

      (in c1 r2)
    )
  )
)