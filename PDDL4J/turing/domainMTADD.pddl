(define (domain MTADD)
	(:requirements :strips :typing)
	(:types
		head cell
	)

	(:predicates
		(at ?h -head ?c - cell)
		(totheleft ?c -cell ?c2 -cell)
		(totheright ?c -cell ?c2 -cell)
		(containsblank ?c)
		(containszero ?c)
		(containsone ?c)
		(statezero ?h)
		(stateone ?h)
		(statehalt ?h)
	)

	(:action statezeroblank
		:parameters (?h -head ?c - cell ?c2 - cell)
		:precondition (and (at ?h ?c) (statezero ?h) (containsblank ?c)
			(totheleft ?c ?c2))
		:effect (and (at ?h ?c2)
			(not (at ?h ?c))
			(stateone ?h)
			(not(statezero ?h))
		)
	)
	(:action statezerozero
		:parameters (?h -head ?c - cell ?c2 - cell)
		:precondition (and (at ?h ?c) (statezero ?h) (containszero ?c)
			(totheright ?c ?c2))
		:effect (and (at ?h ?c2)
			(not (at ?h ?c)))
	)
	(:action statezeroone
		:parameters (?h -head ?c - cell ?c2 - cell)
		:precondition (and (at ?h ?c) (statezero ?h) (containsone ?c)
			(totheright ?c ?c2))
		:effect (and (at ?h ?c2)
			(not (at ?h ?c)))
	)
	(:action stateoneblank
		:parameters (?h -head ?c - cell)
		:precondition (and (at ?h ?c) (stateone ?h) (containsblank ?c)
		)
		:effect (and
			(statehalt ?h)
			(not(stateone ?h))
			(containsone ?c)
			(not (containsblank ?c))

		)
	)
	(:action stateonezero
		:parameters (?h -head ?c - cell)
		:precondition (and (at ?h ?c) (stateone ?h) (containszero ?c)
		)
		:effect(and
			(statehalt ?h)
			(not(stateone ?h))
			(containsone ?c)
			(not(containszero ?c))
		)
	)
	(:action stateoneone
		:parameters (?h -head ?c - cell ?c2 - cell)
		:precondition (and
			(at ?h ?c)
			(stateone ?h)
			(containsone ?c)
			(totheleft ?c ?c2)
		)
		:effect(and
			(containszero ?c)
			(at ?h ?c2)
			(not (at ?h ?c))
			(not(containsone ?c))
		)
	)
)
