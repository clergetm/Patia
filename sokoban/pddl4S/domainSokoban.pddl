(define (domain SOKOBAN)

    (:requirements :strips :typing)

    (:types
        ; wall ; #  
        ; guard ; @ ; (+ is this guard on a storage place)  
        ; box  ; $ ; (* is a box on a storage place)
        ; destination ; .
        ; floor ; " " (space)

        ; objects that can move.
        interactive - object
        guard box - interactive

        ; background objects, where interactive can be on.
        position - object
        floor destination - position

        ; direction the interactive object apply to move.
        direction
    )
    (:constants
        g - guard ; The guard who can push the boxes.
        r l u d - direction ; the four directions.
        )
    (:predicates
        (path ?a ?b - position ?d - direction) ; The interactive objects can go from 'a' to 'b' in direction 'b'
        (on ?i - interactive ?p - position); The interactive object 'i' is on position 'p'
        (withbox ?p - position) ; There is a box on position 'p'
        (empty ?p - position) ; There is nothing on position 'p'
    )

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ;; Interactive methods
    ; ;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ; (:action move ; The guard 'g' moves in one direction 'd' from 'a' to 'b'
    ;     :parameters (?a ?b - position ?d - direction)
    ;     :precondition (and
    ;         (on g ?a) ; guard on position 'a'
    ;         (path ?a ?b ?d) ; there is a path between 'a' and 'b'
    ;         (empty ?b) ; position 'b' is empty
    ;     )
    ;     :effect (and
    ;         (on g ?b) ; guard on 'b'
    ;         (not (empty ?b)); --------
    ;         (not (on g ?a)) ; guard not on 'a' anymore
    ;         (empty ?a) ; -----------------------------
    ;     )
    ; )

    ;     (:action push ; The guard 'g' pushes the box 'box' in one direction 'd'
    ;     ; The guard 'g' goes from 'a' to 'b'
    ;     ; The box 'box' goes from 'b' to 'c'
    ;     :parameters (?a ?b ?c - position ?box - box ?d - direction)
    ;     :precondition (and
    ;         (on g ?a) ; guard is on position 'a'
    ;         (withbox ?b) ; box is on position 'b'
    ;         (empty ?c) ; position next to the box is empty
    ;         (path ?a ?b ?d) ; guard can go on box’s position 'b'
    ;         (path ?b ?c ?d) ; box can go on empty position 'c'
    ;     )
    ;     :effect (and
    ;         ; Move the guard
    ;         (not (on g ?a)) ; guard is not on 'a' anymore
    ;         (empty ?a) ; -----------------------------
    ;         (on g ?b) ; guard on 'b'
    ;         ; (not (empty ?b)); commented line because 'b' won’t be empty, first there is the box then there is the guard

    ;         ; move the box
    ;         (not (on ?box ?b)) ; box is not on position 'b'
    ;         (not (withbox ?b)) ; --------------------------
    ;         (on ?box ?c) ; box is on position 'c'
    ;         (not (empty ?c)) ; ------------------
    ;         (withbox ?c) ; ----------------------
    ;     )
    ; )
    
    (:action moveup ; The guard 'g' moves in one direction 'd' from 'a' to 'b'
        :parameters (?a ?b - position)
        :precondition (and
            (on g ?a) ; guard on position 'a'
            (path ?a ?b u) ; there is a path between 'a' and 'b'
            (empty ?b) ; position 'b' is empty
        )
        :effect (and
            (on g ?b) ; guard on 'b'
            (not (empty ?b)); --------
            (not (on g ?a)) ; guard not on 'a' anymore
            (empty ?a) ; -----------------------------
        )
    )

    (:action movedown ; The guard 'g' moves in one direction 'd' from 'a' to 'b'
        :parameters (?a ?b - position)
        :precondition (and
            (on g ?a) ; guard on position 'a'
            (path ?a ?b d) ; there is a path between 'a' and 'b'
            (empty ?b) ; position 'b' is empty
        )
        :effect (and
            (on g ?b) ; guard on 'b'
            (not (empty ?b)); --------
            (not (on g ?a)) ; guard not on 'a' anymore
            (empty ?a) ; -----------------------------
        )
    )

    (:action moveleft ; The guard 'g' moves in one direction 'd' from 'a' to 'b'
        :parameters (?a ?b - position)
        :precondition (and
            (on g ?a) ; guard on position 'a'
            (path ?a ?b l) ; there is a path between 'a' and 'b'
            (empty ?b) ; position 'b' is empty
        )
        :effect (and
            (on g ?b) ; guard on 'b'
            (not (empty ?b)); --------
            (not (on g ?a)) ; guard not on 'a' anymore
            (empty ?a) ; -----------------------------
        )
    )

    (:action moveright ; The guard 'g' moves in one direction 'd' from 'a' to 'b'
        :parameters (?a ?b - position)
        :precondition (and
            (on g ?a) ; guard on position 'a'
            (path ?a ?b r) ; there is a path between 'a' and 'b'
            (empty ?b) ; position 'b' is empty
        )
        :effect (and
            (on g ?b) ; guard on 'b'
            (not (empty ?b)); --------
            (not (on g ?a)) ; guard not on 'a' anymore
            (empty ?a) ; -----------------------------
        )
    )

    (:action pushup ; The guard 'g' pushes the box 'box' in one direction 'd'
        ; The guard 'g' goes from 'a' to 'b'
        ; The box 'box' goes from 'b' to 'c'
        :parameters (?a ?b ?c - position ?box - box)
        :precondition (and
            (on g ?a) ; guard is on position 'a'
            (withbox ?b) ; box is on position 'b'
            (empty ?c) ; position next to the box is empty
            (path ?a ?b u) ; guard can go on box’s position 'b'
            (path ?b ?c u) ; box can go on empty position 'c'
        )
        :effect (and
            ; Move the guard
            (not (on g ?a)) ; guard is not on 'a' anymore
            (empty ?a) ; -----------------------------
            (on g ?b) ; guard on 'b'
            ; (not (empty ?b)); commented line because 'b' won’t be empty, first there is the box then there is the guard

            ; move the box
            (not (on ?box ?b)) ; box is not on position 'b'
            (not (withbox ?b)) ; --------------------------
            (on ?box ?c) ; box is on position 'c'
            (not (empty ?c)) ; ------------------
            (withbox ?c) ; ----------------------
        )
    )

    (:action pushdown ; The guard 'g' pushes the box 'box' in one direction 'd'
        ; The guard 'g' goes from 'a' to 'b'
        ; The box 'box' goes from 'b' to 'c'
        :parameters (?a ?b ?c - position ?box - box)
        :precondition (and
            (on g ?a) ; guard is on position 'a'
            (withbox ?b) ; box is on position 'b'
            (empty ?c) ; position next to the box is empty
            (path ?a ?b d) ; guard can go on box’s position 'b'
            (path ?b ?c d) ; box can go on empty position 'c'
        )
        :effect (and
            ; Move the guard
            (not (on g ?a)) ; guard is not on 'a' anymore
            (empty ?a) ; -----------------------------
            (on g ?b) ; guard on 'b'
            ; (not (empty ?b)); commented line because 'b' won’t be empty, first there is the box then there is the guard

            ; move the box
            (not (on ?box ?b)) ; box is not on position 'b'
            (not (withbox ?b)) ; --------------------------
            (on ?box ?c) ; box is on position 'c'
            (not (empty ?c)) ; ------------------
            (withbox ?c) ; ----------------------
        )
    )

    (:action pushleft ; The guard 'g' pushes the box 'box' in one direction 'd'
        ; The guard 'g' goes from 'a' to 'b'
        ; The box 'box' goes from 'b' to 'c'
        :parameters (?a ?b ?c - position ?box - box)
        :precondition (and
            (on g ?a) ; guard is on position 'a'
            (withbox ?b) ; box is on position 'b'
            (empty ?c) ; position next to the box is empty
            (path ?a ?b l) ; guard can go on box’s position 'b'
            (path ?b ?c l) ; box can go on empty position 'c'
        )
        :effect (and
            ; Move the guard
            (not (on g ?a)) ; guard is not on 'a' anymore
            (empty ?a) ; -----------------------------
            (on g ?b) ; guard on 'b'
            ; (not (empty ?b)); commented line because 'b' won’t be empty, first there is the box then there is the guard

            ; move the box
            (not (on ?box ?b)) ; box is not on position 'b'
            (not (withbox ?b)) ; --------------------------
            (on ?box ?c) ; box is on position 'c'
            (not (empty ?c)) ; ------------------
            (withbox ?c) ; ----------------------
        )
    )

    (:action pushright ; The guard 'g' pushes the box 'box' in one direction 'd'
        ; The guard 'g' goes from 'a' to 'b'
        ; The box 'box' goes from 'b' to 'c'
        :parameters (?a ?b ?c - position ?box - box)
        :precondition (and
            (on g ?a) ; guard is on position 'a'
            (withbox ?b) ; box is on position 'b'
            (empty ?c) ; position next to the box is empty
            (path ?a ?b r) ; guard can go on box’s position 'b'
            (path ?b ?c r) ; box can go on empty position 'c'
        )
        :effect (and
            ; Move the guard
            (not (on g ?a)) ; guard is not on 'a' anymore
            (empty ?a) ; -----------------------------
            (on g ?b) ; guard on 'b'
            ; (not (empty ?b)); commented line because 'b' won’t be empty, first there is the box then there is the guard

            ; move the box
            (not (on ?box ?b)) ; box is not on position 'b'
            (not (withbox ?b)) ; --------------------------
            (on ?box ?c) ; box is on position 'c'
            (not (empty ?c)) ; ------------------
            (withbox ?c) ; ----------------------
        )
    )


    ;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ;; Reverse methods
    ;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ; (:action reverse_rl ; reverse right to left
    ;     :parameters (?a ?b - position)
    ;     :precondition (and
    ;         (path ?a ?b r)
    ;     )
    ;     :effect (and
    ;         (path ?b ?a l)
    ;     )
    ; )

    ; (:action reverse_lr ; reverse left to right
    ;     :parameters (?a ?b - position)
    ;     :precondition (and
    ;         (path ?a ?b l)
    ;     )
    ;     :effect (and
    ;         (path ?b ?a r)
    ;     )
    ; )

    ; (:action reverse_ud ; reverse up to down
    ;     :parameters (?a ?b - position)
    ;     :precondition (and
    ;         (path ?a ?b u)
    ;     )
    ;     :effect (and
    ;         (path ?b ?a d)
    ;     )
    ; )

    ; (:action reverse_du ; reverse down to up
    ;     :parameters (?a ?b - position)
    ;     :precondition (and
    ;         (path ?a ?b d)
    ;     )
    ;     :effect (and
    ;         (path ?b ?a u)
    ;     )
    ; )
)