(define (domain ROOM)
	(:requirements :strips :typing)
	(:types
		robot location container
	)

	(:predicates
		(at ?r -robot ?l - location)
		(adjacent ?l - location ?l2 -location)
		(unloaded ?r - robot)
		(loaded ?r - robot ?c - container)
		(in ?c - container ?l-location)
	)

	(:action move
		:parameters ( ?r - robot ?l - location ?l2 - location)
		:precondition (and (at ?r ?l)(adjacent ?l ?l2))
		:effect (and (at ?r ?l2)
			(not (at ?r ?l)))
	)

	( :action load
		:parameters ( ?r - robot ?l - location ?c - container)
		:precondition (and (at ?r ?l)(in ?c ?l)(unloaded ?r))
		:effect (and (loaded ?r ?c)
			(not (in ?c ?l)) (not (unloaded ?r))))
	( :action unload
		:parameters ( ?r - robot ?l - location ?c - container)
		:precondition (and (at ?r ?l)(loaded ?r ?c))
		:effect (and (unloaded ?r)
		(in ?c ?l)
			(not (loaded ?r ?c))
			)
	)
)