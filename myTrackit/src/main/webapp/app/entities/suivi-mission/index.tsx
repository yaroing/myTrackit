import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SuiviMission from './suivi-mission';
import SuiviMissionDetail from './suivi-mission-detail';
import SuiviMissionUpdate from './suivi-mission-update';
import SuiviMissionDeleteDialog from './suivi-mission-delete-dialog';

const SuiviMissionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SuiviMission />} />
    <Route path="new" element={<SuiviMissionUpdate />} />
    <Route path=":id">
      <Route index element={<SuiviMissionDetail />} />
      <Route path="edit" element={<SuiviMissionUpdate />} />
      <Route path="delete" element={<SuiviMissionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SuiviMissionRoutes;
