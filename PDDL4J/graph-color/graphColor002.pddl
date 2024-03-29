(define (problem GraphColor-0-2)
    (:domain GRAPHCOLOR)
    (:objects
        wa nt sa q nsw v t - vertice bleu orange violet - color
    )

    (:init
        (connected wa nt)
        (connected wa sa)
        (connected nt q)
        (connected nt sa)
        (connected sa q)
        (connected q nsw)
        (connected sa nsw)
        (connected v nsw)
        (connected v sa)
        (notColored wa)
        (notColored nt)
        (notColored sa)
        (notColored q)
        (notColored nsw)
        (notColored v)
        (different bleu orange)
        (different orange violet)
        (different violet bleu)

        ; t is out of the main graph
        (colored t)
    )

    (:goal
        (and
            (colored wa)
            (colored nt)
            (colored sa)
            (colored q)
            (colored nsw)
            (colored v)
            (differentVertices wa nt)
            (differentVertices wa sa)
            (differentVertices nt q)
            (differentVertices nt sa)
            (differentVertices sa q)
            (differentVertices q nsw)
            (differentVertices sa nsw)
            (differentVertices v nsw)
            (differentVertices v sa)
        )
    )
)
