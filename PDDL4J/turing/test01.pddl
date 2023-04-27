(define (problem MTADD-0-1)
  (:domain MTADD)
  (:objects
   c0 c1 c2 c3 - cell
    h1 - head
  )

  (:init
    (at h1 c1)
    (totheright c0 c1)
    (totheleft c1 c0)
    (totheright c1 c2)
    (totheright c2 c3)
    (totheleft c3 c2)
    (totheleft c2 c1)
    (containszero c1)
    (containsone c2)
    (containsblank c3)
    (statezero h1)
  )

  (:goal
    (and
      (statehalt h1)
    )
  )
)
