import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mission from './mission';
import MissionDetail from './mission-detail';
import MissionUpdate from './mission-update';
import MissionDeleteDialog from './mission-delete-dialog';

const MissionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mission />} />
    <Route path="new" element={<MissionUpdate />} />
    <Route path=":id">
      <Route index element={<MissionDetail />} />
      <Route path="edit" element={<MissionUpdate />} />
      <Route path="delete" element={<MissionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MissionRoutes;
