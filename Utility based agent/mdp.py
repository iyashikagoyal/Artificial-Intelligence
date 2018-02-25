class MDP:

    """A Markov Decision Process, defined by an states, actions, transition model and reward function."""

    def __init__(self, transition={}, reward={}, gamma=.9):
        self.states = transition.keys()
        self.transition = transition
        self.reward = reward
        self.gamma = gamma

    def R(self, state):
        """Return reward for this state."""
        return self.reward[state]

    def actions(self, state):
        """Set of actions that can be performed in this state"""
        return self.transition[state].keys()

    def T(self, state, action):
        """Transition model. For a state and an action, return a list of (probability, result-state) pairs."""
        return self.transition[state][action]



Tr = {
    '(0, 1)': {
        'U': [(0.8, '(0, 2)'), (0.1, '(0, 1)'), (0.1, '(0, 1)')],
        'D': [(0.8, '(0, 0)'), (0.1, '(0, 1)'), (0.1, '(0, 1)')],
        'R': [(0.8, '(0, 1)'), (0.1, '(0, 0)'), (0.1, '(0, 2)')],
        'L': [(0.8, '(0, 1)'), (0.1, '(0, 2)'), (0.1, '(0, 0)')]
    },
    '(3, 0)': {
        'U': [(0.8, '(3, 1)'), (0.1, '(3, 0)'), (0.1, '(2, 0)')],
        'D': [(0.8, '(3, 0)'), (0.1, '(2, 0)'), (0.1, '(3, 0)')],
        'R': [(0.8, '(3, 0)'), (0.1, '(3, 0)'), (0.1, '(3, 1)')],
        'L': [(0.8, '(2, 0)'), (0.1, '(3, 1)'), (0.1, '(3, 0)')]
    },
    '(0, 0)': {
        'U': [(0.8, '(0, 1)'), (0.1, '(1, 0)'), (0.1, '(0, 0)')],
        'D': [(0.8, '(0, 0)'), (0.1, '(0, 0)'), (0.1, '(1, 0)')],
        'R': [(0.8, '(1, 0)'), (0.1, '(0, 0)'), (0.1, '(0, 1)')],
        'L': [(0.8, '(0, 0)'), (0.1, '(0, 1)'), (0.1, '(0, 0)')]
    },
    '(1, 0)': {
        'U': [(0.8, '(1, 0)'), (0.1, '(2, 0)'), (0.1, '(0, 0)')],
        'D': [(0.8, '(1, 0)'), (0.1, '(0, 0)'), (0.1, '(2, 0)')],
        'R': [(0.8, '(2, 0)'), (0.1, '(1, 0)'), (0.1, '(1, 0)')],
        'L': [(0.8, '(0, 0)'), (0.1, '(1, 0)'), (0.1, '(1, 0)')]
    },
    '(3, 1)': {
        'EXIT': [(0.0, '(3, 1)')]
    },
    '(2, 1)': {
        'U': [(0.8, '(2, 2)'), (0.1, '(3, 1)'), (0.1, '(2, 1)')],
        'D': [(0.8, '(2, 0)'), (0.1, '(2, 1)'), (0.1, '(3, 1)')],
        'R': [(0.8, '(3, 1)'), (0.1, '(2, 0)'), (0.1, '(2, 2)')],
        'L': [(0.8, '(2, 1)'), (0.1, '(2, 2)'), (0.1, '(2, 0)')]
    },
    '(1, 2)': {
        'U': [(0.8, '(1, 2)'), (0.1, '(2, 2)'), (0.1, '(0, 2)')],
        'D': [(0.8, '(1, 2)'), (0.1, '(0, 2)'), (0.1, '(2, 2)')],
        'R': [(0.8, '(2, 2)'), (0.1, '(1, 2)'), (0.1, '(1, 2)')],
        'L': [(0.8, '(0, 2)'), (0.1, '(1, 2)'), (0.1, '(1, 2)')]
    },
    '(2, 0)': {
        'U': [(0.8, '(2, 1)'), (0.1, '(3, 0)'), (0.1, '(1, 0)')],
        'D': [(0.8, '(2, 0)'), (0.1, '(1, 0)'), (0.1, '(3, 0)')],
        'R': [(0.8, '(3, 0)'), (0.1, '(2, 0)'), (0.1, '(2, 1)')],
        'L': [(0.8, '(1, 0)'), (0.1, '(2, 1)'), (0.1, '(2, 0)')]
    },
    '(3, 2)': {
        'EXIT': [(0.0, '(3, 2)')]
    },
    '(0, 2)': {
        'U': [(0.8, '(0, 2)'), (0.1, '(1, 2)'), (0.1, '(0, 2)')],
        'D': [(0.8, '(0, 1)'), (0.1, '(0, 2)'), (0.1, '(1, 2)')],
        'R': [(0.8, '(1, 2)'), (0.1, '(0, 1)'), (0.1, '(0, 2)')],
        'L': [(0.8, '(0, 2)'), (0.1, '(0, 2)'), (0.1, '(0, 1)')]
    },
    '(2, 2)': {
        'U': [(0.8, '(2, 2)'), (0.1, '(3, 2)'), (0.1, '(1, 2)')],
        'D': [(0.8, '(2, 1)'), (0.1, '(1, 2)'), (0.1, '(3, 2)')],
        'R': [(0.8, '(3, 2)'), (0.1, '(2, 1)'), (0.1, '(2, 2)')],
        'L': [(0.8, '(1, 2)'), (0.1, '(2, 2)'), (0.1, '(2, 1)')]
    }
}
Reward = {
    '(0, 1)': -0.04,
    '(3, 0)': -0.04,
    '(0, 0)': -0.04,
    '(1, 0)': -0.04,
    '(3, 1)': -10,
    '(1, 1)': None,
    '(2, 1)': -0.04,
    '(1, 2)': -0.04,
    '(2, 0)': -0.04,
    '(3, 2)': 10,
    '(0, 2)': -0.04,
    '(2, 2)': -0.04
}

mdp = MDP(transition=Tr,reward=Reward)

def value_iteration(epsilon=0.001):
    """ Solving an MDP by value iteration. """
    states = mdp.states
    actions = mdp.actions
    T = mdp.T
    R = mdp.R

    U1 = {s: 0 for s in states}
    gamma = 0.9
    while True:
        U = U1.copy()
        delta = 0
        for s in states:
            U1[s] = R(s) + gamma * max([ sum([p * U[s1] for (p, s1) in T(s, a)]) for a in actions(s)])
            delta = max(delta, abs(U1[s] - U[s]))

        if delta < epsilon * (1 - gamma) / gamma:
            return U


def best_policy(U):
    """Given an MDP and a utility function U, determine the best policy, as a mapping from state to action."""
    states = mdp.states
    actions = mdp.actions
    pi = {}
    for s in states:
        pi[s] = max(actions(s), key=lambda a: expected_utility(a, s, U))
    return pi


def expected_utility(a, s, U):
    """The expected utility of doing a in state s, according to the MDP and U."""
    T = mdp.T
    return sum([p * U[s1] for (p, s1) in mdp.T(s, a)])


pi = best_policy(value_iteration(.01))
print pi
